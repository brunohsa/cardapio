package br.com.unip.cardapio.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "subcategoria")
class Subcategoria {

    @Id
    var id: String? = null

    var nome: String? = null

    var descricao: String? = null

    constructor(id: String?, nome: String?, descricao: String?) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
    }
}