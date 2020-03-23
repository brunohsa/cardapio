package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class InfoCardapioResponse {

    @JsonProperty(value = "id")
    val id: String?

    @JsonProperty(value = "nome")
    val nome: String?

    constructor(id: String?, nome: String?) {
        this.id = id
        this.nome = nome
    }
}