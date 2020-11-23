package br.com.unip.cardapio.dto

class CategoriaDTO {

    var id: String? = ""

    var titulo: String? = ""

    var subcategoria: SubcategoriaDTO?

    var produtos: List<ProdutoDTO>? = emptyList()

    constructor(id: String?, titulo: String?, subcategoria: SubcategoriaDTO?, produtos: List<ProdutoDTO>)
            : this(titulo, subcategoria) {
        this.id = id
        this.produtos = produtos
    }

    constructor(titulo: String?, subcategoria: SubcategoriaDTO?) {
        this.titulo = titulo
        this.subcategoria = subcategoria
    }
}