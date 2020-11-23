package br.com.unip.cardapio.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CardapioRequest {

    @JsonProperty("nome")
    var nome: String? = ""

    @JsonProperty("ativo")
    var ativo: Boolean = false

    constructor()

    constructor(nome: String?, ativo: Boolean) {
        this.nome = nome
        this.ativo = ativo
    }
}