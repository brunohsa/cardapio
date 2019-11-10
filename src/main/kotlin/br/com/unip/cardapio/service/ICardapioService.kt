package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.ProdutoDTO

interface ICardapioService {

    fun criar(dto: CardapioDTO): String

    fun adicionarProduto(idCardapio: String?, dto: ProdutoDTO): CardapioDTO

    fun buscar(): CardapioDTO
}