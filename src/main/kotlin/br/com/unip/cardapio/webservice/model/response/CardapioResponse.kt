package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CardapioResponse {

    @JsonProperty(value = "id")
    val id: String?

    @JsonProperty(value = "nome")
    val nome: String?

    @JsonProperty(value = "produtos")
    val produtos: List<ProdutoResponse>

    constructor(id: String?, nome: String?, produtos: List<ProdutoResponse>) {
        this.id = id
        this.nome = nome
        this.produtos = produtos
    }
}