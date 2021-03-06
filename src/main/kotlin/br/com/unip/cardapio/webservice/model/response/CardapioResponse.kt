package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CardapioResponse {

    @JsonProperty(value = "id")
    val id: String?

    @JsonProperty(value = "nome")
    val nome: String?

    @JsonProperty(value = "ativo")
    val ativo: Boolean

    @JsonProperty(value = "categorias")
    val categorias: List<CategoriaResponse>

    constructor(id: String?, nome: String?, categorias: List<CategoriaResponse>, ativo: Boolean) {
        this.id = id
        this.nome = nome
        this.categorias = categorias
        this.ativo = ativo
    }
}