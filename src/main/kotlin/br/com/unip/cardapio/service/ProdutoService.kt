package br.com.unip.cardapio.service

import br.com.unip.cardapio.domain.ProdutoDomain
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro
import br.com.unip.cardapio.exception.ECodigoErro.ID_PRODUTO_OBRIGATORIO
import br.com.unip.cardapio.exception.ProdutoNaoEncontradoException
import br.com.unip.cardapio.repository.IProdutoRepository
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.repository.entity.enums.ECategoriaProduto
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Base64
import java.util.UUID

@Service
class ProdutoService(val produtoRepository: IProdutoRepository) : IProdutoService {

    val PATH_PASTA_BASE: String = "/opt/imagens/"

    override fun cadastrar(uuidFornecedor: String?, dto: ProdutoDTO): Produto {
        val produtoDomain = ProdutoDomain(dto.nome, dto.descricao, dto.valor, dto.categoria, dto.imagem)
        val absolutePath = this.salvarImagem(uuidFornecedor, produtoDomain.imagem.get())

        val produto = Produto(produtoDomain.nome.get(), produtoDomain.descricao.get(), produtoDomain.valor.get(),
                produtoDomain.categoria.get(), absolutePath)

        return produtoRepository.save(produto)
    }

    override fun alterar(id: String?, produtoDTO: ProdutoDTO): Produto {
        if (id == null) {
            throw CampoObrigatorioException(ID_PRODUTO_OBRIGATORIO)
        }
        val produto = produtoRepository.findById(id).orElseThrow { ProdutoNaoEncontradoException() }
        if (!produtoDTO.categoria.isNullOrBlank()) {
            produto.categoria = ECategoriaProduto.valueOf(produtoDTO.categoria!!)
        }
        if (!produtoDTO.descricao.isNullOrEmpty()) {
            produto.descricao = produtoDTO.descricao
        }
        if (!produtoDTO.nome.isNullOrEmpty()) {
            produto.nome = produtoDTO.nome
        }
        if (!produtoDTO.valor.isNullOrEmpty()) {
            produto.valor = BigDecimal(produtoDTO.valor)
        }

        return produtoRepository.save(produto)
    }

    override fun alterarImagem(cardapioId: String?, produtoId: String?, imagemBase64: String) {
        if (produtoId == null) {
            throw CampoObrigatorioException(ID_PRODUTO_OBRIGATORIO)
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
            throw CampoObrigatorioException(ID_PRODUTO_OBRIGATORIO)
        }
        val produto = produtoRepository.findById(produtoId).orElseThrow { ProdutoNaoEncontradoException() }
        val imagem = File(produto.urlImagem)
        return InputStreamResource(FileInputStream(imagem))
    }

    override fun buscarProduto(id: String?): ProdutoDTO {
        if (id == null) {
            throw CampoObrigatorioException(ID_PRODUTO_OBRIGATORIO)
        }
        val produto = produtoRepository.findById(id).orElseThrow { ProdutoNaoEncontradoException() }
        return ProdutoDTO(produto.id, produto.nome, produto.descricao, produto.valor.toString(),
                produto.categoria.toString(), produto.urlImagem)
    }
}