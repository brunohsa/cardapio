package br.com.unip.cardapio.domain

import br.com.unip.cardapio.domain.campos.Descricao
import br.com.unip.cardapio.domain.campos.Estoque
import br.com.unip.cardapio.domain.campos.Nome
import br.com.unip.cardapio.domain.campos.Valor

class ProdutoDomain {

    val nome: Nome

    val descricao: Descricao

    val valor: Valor

    val estoque: Estoque

    constructor(nome: String?,
                descricao: String?,
                valor: String?,
                estoque: Int?) {
        this.nome = Nome(nome)
        this.descricao = Descricao(descricao)
        this.valor = Valor(valor)
        this.estoque = Estoque(estoque)
    }
}