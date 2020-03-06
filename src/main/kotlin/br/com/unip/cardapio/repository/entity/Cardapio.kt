package br.com.unip.cardapio.repository.entity

import br.com.unip.cardapio.repository.persistence.CascadeSave
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cardapio")
class Cardapio {

    @Id
    var id: String? = null

    var nome: String? = null

    var uuidFornecedor: String? = null

    @DBRef
    @CascadeSave
    var produtos: List<Produto> = emptyList()

    var ativo: Boolean = false

    constructor()


    constructor(nome: String?, uuidFornecedor: String?, produtos: List<Produto>, ativo: Boolean)
            : this(nome, uuidFornecedor, ativo) {
        this.produtos = produtos
    }

    constructor(id: String?, nome: String?, ativo: Boolean) {
        this.id = id
        this.nome = nome
        this.ativo = ativo
    }
}