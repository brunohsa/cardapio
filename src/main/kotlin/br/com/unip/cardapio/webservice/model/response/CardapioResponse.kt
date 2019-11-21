package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CardapioResponse {

    @JsonProperty(value = "cardapio")
    val infoCardapio: InfoCardapioResponse

    @JsonProperty(value = "produtos")
    val produtos: List<ProdutoResponse>

    constructor(infoCardapio: InfoCardapioResponse, produtos: List<ProdutoResponse>) {
        this.infoCardapio = infoCardapio
        this.produtos = produtos
    }
}