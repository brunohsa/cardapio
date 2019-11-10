package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProdutoResponse {

    @JsonProperty(value = "id")
    var id: String?

    @JsonProperty(value = "nome")
    var nome: String?

    @JsonProperty(value = "descricao")
    var descricao: String?

    @JsonProperty(value = "valor")
    var valor: String?

    @JsonProperty(value = "categoria")
    var categoria: String?

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, categoria: String?) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.valor = valor
        this.categoria = categoria
    }
}