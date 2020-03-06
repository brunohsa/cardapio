package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.entity.Produto
import org.springframework.core.io.InputStreamResource

interface IProdutoService {

    fun cadastrar(uuidFornecedor: String?, dto: ProdutoDTO): Produto

    fun remover(produto: Produto)

    fun alterar(id : String?, produtoDTO: ProdutoDTO) : Produto

    fun alterarImagem(cardapioId: String?, produtoId: String?, imagemBase64: String)

    fun downloadImagem(produtoId: String?) : InputStreamResource

    fun buscarProduto(id: String?): ProdutoDTO
}