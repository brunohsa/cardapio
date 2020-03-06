package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.ProdutoDTO

interface ICardapioService {

    fun criar(dto: CardapioDTO): String

    fun adicionarProduto(idCardapio: String, dto: ProdutoDTO): CardapioDTO

    fun alterarProduto(idCardapio: String, idProduto: String, produtoDTO: ProdutoDTO)

    fun buscar(): CardapioDTO?

    fun removerProduto(idCardapio: String?, idProduto: String?)
}