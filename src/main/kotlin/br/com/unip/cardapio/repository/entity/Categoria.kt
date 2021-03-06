package br.com.unip.cardapio.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "categoria")
class Categoria {

    @Id
    var id: String? = null

    var titulo: String? = null

    @DBRef
    var produtos: List<Produto> = emptyList()

    var cardapioId: String? = null

    @DBRef
    var subcategoria: Subcategoria? = null

    constructor()

    constructor(titulo: String?, subcategoria: Subcategoria?, cardapioId: String?) {
        this.titulo = titulo
        this.cardapioId = cardapioId
        this.subcategoria = subcategoria
    }


    fun adicionarProduto(produto: Produto) {
        this.produtos = produtos.plus(produto)
    }

    fun removerProduto(produto: Produto) {
        val produtos = this.produtos.toMutableList()
        produtos.remove(produto)

        this.produtos = produtos
    }
}