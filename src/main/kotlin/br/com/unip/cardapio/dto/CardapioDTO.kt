package br.com.unip.cardapio.dto

class CardapioDTO {

    var id: String? = ""

    var titulo: String? = ""

    var categorias: List<CategoriaDTO>? = emptyList()

    constructor(nome: String?) {
        this.titulo = nome
    }

    constructor(id: String?, titulo: String?, categorias: List<CategoriaDTO>?) {
        this.id = id
        this.titulo = titulo
        this.categorias = categorias
    }
}

