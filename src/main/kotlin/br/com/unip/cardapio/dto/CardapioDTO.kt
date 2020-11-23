package br.com.unip.cardapio.dto

class CardapioDTO {

    var id: String? = ""

    var titulo: String? = ""

    var categorias: List<CategoriaDTO>? = emptyList()

    var ativo: Boolean = false

    constructor(nome: String?, ativo: Boolean) {
        this.titulo = nome
        this.ativo = ativo
    }

    constructor(id: String?, titulo: String?, ativo: Boolean, categorias: List<CategoriaDTO>?) {
        this.id = id
        this.titulo = titulo
        this.categorias = categorias
        this.ativo = ativo
    }
}

