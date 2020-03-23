package br.com.unip.cardapio.dto

class CategoriaDTO {

    var id: String? = ""

    var titulo: String? = ""

    var produtos: List<ProdutoDTO>? = emptyList()

    constructor(id: String?, titulo: String?, produtos: List<ProdutoDTO>) : this(titulo) {
        this.id = id
        this.produtos = produtos
    }

    constructor(titulo: String?) {
        this.titulo = titulo
    }
}