package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO

interface ICardapioService {

    fun criar(dto: CardapioDTO): String

    fun buscar(): CardapioDTO

    fun adicionarCategoria(dto: CategoriaDTO): CardapioDTO

    fun removerCategoria(idCategoria: String)

    fun adicionarProduto(idCategoria: String, produtoDTO: ProdutoDTO)

    fun alterarProduto(idCategoria: String, idProduto: String, produtoDTO: ProdutoDTO)

    fun removerProduto(idProduto: String, idCategoria: String)
}