package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.InserirCategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO

interface ICardapioService {

    fun criar(dto: CardapioDTO): String

    fun remover(idCardapio: String)

    fun buscar(id: String): CardapioDTO

    fun buscarPorFornecedorUUID(fonecedorUUID: String): CardapioDTO?

    fun buscarCardapios(): List<CardapioDTO>

    fun alterar(idCardapio: String, cardapioDTO: CardapioDTO)

    fun adicionarCategoria(dto: InserirCategoriaDTO, idCardapio: String): CardapioDTO

    fun removerCategoria(idCategoria: String, idCardapio: String)

    fun adicionarProduto(idCategoria: String, produtoDTO: ProdutoDTO, idCardapio: String)

    fun alterarProduto(idCategoria: String, produtoDTO: ProdutoDTO)

    fun removerProduto(idProduto: String, idCategoria: String, idCardapio: String)
}