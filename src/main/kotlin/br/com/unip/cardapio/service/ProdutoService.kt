package br.com.unip.cardapio.service

import br.com.unip.cardapio.domain.ProdutoDomain
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro
import br.com.unip.cardapio.exception.ProdutoNaoEncontradoException
import br.com.unip.cardapio.repository.IProdutoRepository
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.repository.entity.enums.ECategoriaProduto
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils
import java.io.File
import java.io.FileInputStream
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class ProdutoService(val produtoRepository: IProdutoRepository) : IProdutoService {

    val PATH_PASTA_BASE: String = "/opt/imagens/"

    override fun cadastrar(uuidFornecedor: String?, dto: ProdutoDTO): Produto {
        val produtoDomain = ProdutoDomain(dto.nome, dto.descricao, dto.valor, dto.categoria, dto.imagem)
        val absolutePath = this.salvarImagem(uuidFornecedor, produtoDomain.imagem.get())

        val produto = Produto(produtoDomain.nome.get(),
                produtoDomain.descricao.get(),
                produtoDomain.valor.get(),
                produtoDomain.categoria.get(),
                absolutePath
        )

        return produtoRepository.save(produto)
    }

    override fun editar(produtoId: String?, dto: ProdutoDTO) {
        if (produtoId == null) {
            throw CampoObrigatorioException("Id do produto é obrigatório.", ECodigoErro.CAD019)
        }
        val produto = produtoRepository.findById(produtoId).orElseThrow { ProdutoNaoEncontradoException() }
        if (!dto.categoria.isNullOrBlank()) {
            produto.categoria = ECategoriaProduto.valueOf(dto.categoria!!)
        }
        if (!dto.descricao.isNullOrEmpty()) {
            produto.descricao = dto.descricao
        }
        if (!dto.nome.isNullOrEmpty()) {
            produto.nome = dto.nome
        }
        if (!dto.valor.isNullOrEmpty()) {
            produto.valor = BigDecimal(dto.valor)
        }

        produtoRepository.save(produto)
    }

    override fun alterarImagem(cardapioId: String?, produtoId: String?, imagemBase64: String) {
        if (produtoId == null) {
            throw CampoObrigatorioException("Id do produto é obrigatório.", ECodigoErro.CAD019)
        }
        val produto = produtoRepository.findById(produtoId).orElseThrow { ProdutoNaoEncontradoException() }
        //deleta arquivo antigo
        Files.deleteIfExists(Paths.get(produto.urlImagem))

        val imagemSalva = this.salvarImagem(cardapioId, imagemBase64)
        produto.urlImagem = imagemSalva

        produtoRepository.save(produto)
    }

    override fun remover(produto: Produto) {
        produtoRepository.delete(produto)
    }

    private fun salvarImagem(cardapioId: String?, imagemBase64: String): String {
        val path = PATH_PASTA_BASE + cardapioId
        this.criarPasta(path)

        val imagem: ByteArray = Base64.getDecoder().decode(imagemBase64)

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
        if (produtoId == null) {
            throw CampoObrigatorioException("Id do produto é obrigatório.", ECodigoErro.CAD019)
        }
        val produto = produtoRepository.findById(produtoId).orElseThrow { ProdutoNaoEncontradoException() }
        val imagem = File(produto.urlImagem)
        return InputStreamResource(FileInputStream(imagem))
    }

    override fun buscarProduto(id: String?): ProdutoDTO {
        if (id == null) {
            throw CampoObrigatorioException("Id do produto é obrigatório.", ECodigoErro.CAD019)
        }
        val produto = produtoRepository.findById(id).orElseThrow { ProdutoNaoEncontradoException() }
        return ProdutoDTO(produto.id, produto.descricao, produto.valor.toString(), produto.categoria.toString(), produto.urlImagem)
    }
}