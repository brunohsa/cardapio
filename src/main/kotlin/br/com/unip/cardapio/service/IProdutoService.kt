package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.entity.Produto
import org.springframework.core.io.InputStreamResource

interface IProdutoService {

    fun cadastrar(dto: ProdutoDTO, categoriaId: String): Produto

    fun remover(id: String, categoriaId: String)

    fun alterar(id: String, categoriaId: String, produtoDTO: ProdutoDTO): Produto

    fun alterarImagem(produtoId: String?, imagemBase64: String)

    fun downloadImagem(produtoId: String?): InputStreamResource

    fun buscar(id: String?): ProdutoDTO
}