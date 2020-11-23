package br.com.unip.cardapio.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.cardapio.domain.ProdutoDomain
import br.com.unip.cardapio.dto.FiltroProdutosDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro.ID_PRODUTO_OBRIGATORIO
import br.com.unip.cardapio.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.repository.IProdutoRepository
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.repository.entity.Subcategoria
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import javax.xml.bind.DatatypeConverter

@Service
class ProdutoService(val produtoRepository: IProdutoRepository,
                     val mongoTemplate: MongoTemplate,
                     @Value("\${download.imagens.url}") val urlDownload: String) : IProdutoService {

    private val PATH_PASTA_BASE: String = "/opt/imagens"

    override fun buscarMelhoresAvaliados(cardapiosIds: List<String?>): List<ProdutoDTO> {
        val filtro = FiltroProdutosDTO(
                notaApartirDe = null,
                notaMenorQue = null,
                precoApartirDe = null,
                precoMenorQue = null,
                nome = null,
                subcategoriaId = null,
                tipoOrdenacao = "DESC",
                campoOrdenacao = "nota",
                limite = 10)
        return this.buscarPorFiltro(cardapiosIds, filtro)
    }

    override fun buscarMaisVendidos(cardapiosIds: List<String?>): List<ProdutoDTO> {
        val filtro = FiltroProdutosDTO(
                notaApartirDe = null,
                notaMenorQue = null,
                precoApartirDe = null,
                precoMenorQue = null,
                nome = null,
                subcategoriaId = null,
                tipoOrdenacao = "DESC",
                campoOrdenacao = "vendidos",
                limite = 10)
        return this.buscarPorFiltro(cardapiosIds, filtro)
    }

    override fun buscarPorFiltro(cardapiosIds: List<String?>, filtro: FiltroProdutosDTO): List<ProdutoDTO> {
        val criteria = Criteria.where("cardapioId").`in`(cardapiosIds)
        if (filtro.nome != null) {
            criteria.and("nome").regex(".*${filtro.nome}.*", "i")
        }
        if (filtro.notaApartirDe != null) {
            criteria.and("nota").gte(filtro.notaApartirDe.toDouble())
        }
        if (filtro.notaMenorQue != null) {
            criteria.and("nota").lt(filtro.notaMenorQue.toDouble())
        }
        if (filtro.precoApartirDe != null) {
            criteria.and("valor").gte(filtro.precoApartirDe.toDouble())
        }
        if (filtro.precoMenorQue != null) {
            criteria.and("valor").lt(filtro.precoMenorQue.toDouble())
        }
        if (filtro.subcategoriaId != null) {
            criteria.and("subcategoriaId").`is`(filtro.subcategoriaId)
        }
        if (filtro.campoOrdenacao != null) {
            criteria.and(filtro.campoOrdenacao).ne(null)
        }

        val query = Query().addCriteria(criteria)
        if (filtro.tipoOrdenacao != null && filtro.campoOrdenacao != null) {
            val tipoOrdenacao = if (filtro.tipoOrdenacao == "ASC") {
                Sort.Direction.ASC
            } else {
                Sort.Direction.DESC
            }
            query.with(Sort(tipoOrdenacao, filtro.campoOrdenacao))
        }
        if (filtro.limite != null) {
            query.limit(filtro.limite)
        }

        val produtos = mongoTemplate.find(query, Produto::class.java)
        return produtos.map { p -> p.toDTO() }
    }

    override fun cadastrar(dto: ProdutoDTO, categoriaId: String, subcategoriaId: String?, cardapioId: String): Produto {
        val produtoDomain = ProdutoDomain(dto.nome, dto.descricao, dto.valor, dto.estoque)
        val produto = Produto(
                nome = produtoDomain.nome.get(),
                descricao = produtoDomain.descricao.get(),
                valor = produtoDomain.valor.get(),
                cardapioId = cardapioId,
                categoriaId = categoriaId,
                subcategoriaId = subcategoriaId,
                estoque = produtoDomain.estoque.get()
        )
        return produtoRepository.save(produto)
    }

    override fun alterar(id: String, categoriaId: String, produtoDTO: ProdutoDTO): Produto {
        val produto = buscarProduto(id)
        if (!produtoDTO.descricao.isNullOrEmpty()) {
            produto.descricao = produtoDTO.descricao
        }
        if (!produtoDTO.nome.isNullOrEmpty()) {
            produto.nome = produtoDTO.nome
        }
        if (!produtoDTO.valor.isNullOrEmpty()) {
            produto.valor = produtoDTO.valor!!.toDouble()
        }
        produto.estoque = produtoDTO.estoque

        return produtoRepository.save(produto)
    }

    override fun atualizarSubcategoria(id: String, subcategoria: Subcategoria) {
        val produto = this.buscarProduto(id)
        produto.subcategoriaId = subcategoria.id
    }

    override fun alterarImagem(produtoId: String?, imagemBase64: String) {
        val produto = buscarProduto(produtoId)

        //deleta arquivo antigo
        if (!produto.urlImagem.isNullOrEmpty()) {
            Files.deleteIfExists(Paths.get(produto.urlImagem))
        }
        val imagemSalva = this.salvarImagem(imagemBase64)
        produto.urlImagem = imagemSalva

        produtoRepository.save(produto)
    }

    override fun remover(id: String, categoriaId: String) {
        val produto = buscarPorIdECategoriaId(id, categoriaId)
        produtoRepository.delete(produto)
    }

    private fun salvarImagem(imagemBase64: String?): String? {
        if (imagemBase64.isNullOrEmpty()) {
            return null
        }
        val cadastroUuid = AuthenticationUtil.getCadastroUUID()

        val path = "$PATH_PASTA_BASE/$cadastroUuid"
        this.criarPasta(path)

        val imagem: ByteArray = DatatypeConverter.parseBase64Binary(imagemBase64.split(",")[1])

        val nomeImagem = "${UUID.randomUUID()}.jpg"
        val arquivo = File("$path/$nomeImagem")

        arquivo.writeBytes(imagem)
        return arquivo.absolutePath
    }

    private fun criarPasta(path: String) {
        val pasta = File(path)
        if (pasta.exists()) {
            return
        }
        pasta.mkdirs()
    }

    override fun downloadImagem(produtoId: String?): InputStreamResource {
        val produto = buscarProduto(produtoId)
        val imagem = File(produto.urlImagem)

        return InputStreamResource(FileInputStream(imagem))
    }

    override fun buscar(id: String?): ProdutoDTO {
        return buscarProduto(id).toDTO()
    }

    private fun buscarProduto(id: String?): Produto {
        if (id == null) {
            throw CampoObrigatorioException(ID_PRODUTO_OBRIGATORIO)
        }
        return produtoRepository.findById(id).orElseThrow { NaoEncontradoException(PRODUTO_NAO_ENCONTRADO) }
    }

    private fun buscarPorIdECategoriaId(id: String, categoriaId: String): Produto {
        return produtoRepository.findByIdAndCategoriaId(id, categoriaId)
                .orElseThrow { NaoEncontradoException(PRODUTO_NAO_ENCONTRADO) }
    }

    private fun montarURLDownloadImagem(produto: Produto): String? {
        if (produto.urlImagem.isNullOrEmpty()) {
            return ""
        }
        return urlDownload.format(produto.id)
    }

    private fun Produto.toDTO() = ProdutoDTO(this.id, this.nome, this.descricao, this.valor.toString(), this.estoque,
            this.vendidos, this.nota, montarURLDownloadImagem(this), this.cardapioId)
}