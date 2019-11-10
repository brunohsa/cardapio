package br.com.unip.cardapio.service

import br.com.unip.cardapio.domain.ProdutoDomain
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.IProdutoRepository
import br.com.unip.cardapio.repository.entity.Produto
import org.springframework.stereotype.Service
import java.io.File
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

    private fun salvarImagem(uuidFornecedor: String?, imagemBase64: String): String {
        val path = PATH_PASTA_BASE + uuidFornecedor
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
}