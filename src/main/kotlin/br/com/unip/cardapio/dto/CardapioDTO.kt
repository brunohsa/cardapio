package br.com.unip.cardapio.dto

class CardapioDTO {

    var id: String? = ""

    var nome: String? = ""

    var produtos: List<ProdutoDTO>? = emptyList()

    constructor(nome: String?) {
        this.nome = nome
    }

    constructor(id: String?, nome: String?, produtos: List<ProdutoDTO>?) {
        this.id = id
        this.nome = nome
        this.produtos = produtos
    }
}

