package br.com.unip.cardapio.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cardapio")
class Cardapio {

    @Id
    var id: String? = null

    var nome: String? = null

    var uuidFornecedor: String? = null

    var produtos: List<Produto> = emptyList()

    var ativo: Boolean? = null

    constructor()

    constructor(nome: String?, uuidFornecedor: String?, produtos: List<Produto>, ativo: Boolean?) {
        this.nome = nome
        this.uuidFornecedor = uuidFornecedor
        this.produtos = produtos
        this.ativo = ativo
    }
}