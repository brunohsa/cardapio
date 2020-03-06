package br.com.unip.cardapio.dto

class CardapioDTO {

    var id: String? = ""

    var titulo: String? = ""

    var produtos: List<ProdutoDTO>? = emptyList()

    constructor(nome: String?) {
        this.titulo = nome
    }

    constructor(id: String?, titulo: String?, produtos: List<ProdutoDTO>?) {
        this.id = id
        this.titulo = titulo
        this.produtos = produtos
    }
}

