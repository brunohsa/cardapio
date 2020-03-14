package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.entity.Categoria

interface ICategoriaService {

    fun adicionar(dto: CategoriaDTO, cardapioId: String): Categoria

    fun remover(categoriaId: String, cardapioId: String)

    fun adicionarProduto(categoriaId: String, cardapioId: String, produtoDTO: ProdutoDTO): Categoria

    fun alterarProduto(idCategoria: String, idProduto: String, dto: ProdutoDTO)

    fun removerProduto(categoriaId: String, cardapioId: String, produtoId: String)
}