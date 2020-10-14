package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO

interface ICardapioService {

    fun criar(dto: CardapioDTO): String

    fun buscar(id: String): CardapioDTO

    fun buscarCardapios(): List<CardapioDTO>

    fun adicionarCategoria(dto: CategoriaDTO, idCardapio: String): CardapioDTO

    fun removerCategoria(idCategoria: String, idCardapio: String)

    fun adicionarProduto(idCategoria: String, produtoDTO: ProdutoDTO, idCardapio: String)

    fun alterarProduto(idCategoria: String, idProduto: String, produtoDTO: ProdutoDTO)

    fun removerProduto(idProduto: String, idCategoria: String, idCardapio: String)
}