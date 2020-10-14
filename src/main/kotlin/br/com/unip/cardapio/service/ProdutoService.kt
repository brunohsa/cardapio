package br.com.unip.cardapio.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.cardapio.domain.ProdutoDomain
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro.ID_PRODUTO_OBRIGATORIO
import br.com.unip.cardapio.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.repository.IProdutoRepository
import br.com.unip.cardapio.repository.entity.Produto
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

    val PATH_PASTA_BASE: String = "/opt/imagens"

    override fun cadastrar(dto: ProdutoDTO, categoriaId: String, cardapioId: String): Produto {
        val produtoDomain = ProdutoDomain(dto.nome, dto.descricao, dto.valor, dto.imagem)
        val absolutePath = this.salvarImagem(produtoDomain.imagem.get())
        val produto = Produto(
                nome = produtoDomain.nome.get(),
                descricao = produtoDomain.descricao.get(),
                valor = produtoDomain.valor.get(),
                urlImagem = absolutePath,
                cardapioId = cardapioId,
                categoriaId =  categoriaId
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
            produto.valor = BigDecimal(produtoDTO.valor)
        }
        return produtoRepository.save(produto)
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
        val produto = buscarProduto(produtoId)
        val imagem = File(produto.urlImagem)

        return InputStreamResource(FileInputStream(imagem))
    }

    override fun buscar(id: String?): ProdutoDTO {
        val produto = buscarProduto(id)
        return ProdutoDTO(produto.id, produto.nome, produto.descricao, produto.valor.toString(), produto.urlImagem)
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
}