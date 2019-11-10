package br.com.unip.cardapio.domain

import br.com.unip.cardapio.domain.campos.Nome

class CardapioDomain {

    val nome: Nome

    constructor(nome: String?) {
        this.nome = Nome(nome)
    }
}