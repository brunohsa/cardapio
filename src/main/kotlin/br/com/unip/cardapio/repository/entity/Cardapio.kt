package br.com.unip.cardapio.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cardapio")
class Cardapio {

    @Id
    var id: String? = null

    var titulo: String? = null

    var uuidFornecedor: String? = null

    @DBRef
    var categorias: List<Categoria> = emptyList()

    var ativo: Boolean = false

    constructor()


    constructor(titulo: String?, uuidFornecedor: String?, categorias: List<Categoria>, ativo: Boolean)
            : this(titulo, uuidFornecedor, ativo) {
        this.categorias = categorias
    }

    constructor(titulo: String?, uuidFornecedor: String?, ativo: Boolean) {
        this.titulo = titulo
        this.uuidFornecedor = uuidFornecedor
        this.ativo = ativo
    }

    fun adicionarCategoria(categoria: Categoria) {
        this.categorias = categorias.plus(categoria)
    }

    fun removerCategoria(categoria: Categoria) {
        val categorias = this.categorias.toMutableList()
        categorias.remove(categoria)

        this.categorias = categorias
    }
}