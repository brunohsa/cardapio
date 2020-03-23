package br.com.unip.cardapio.webservice.model.response.erro

import com.fasterxml.jackson.annotation.JsonProperty

class Erro {

    @JsonProperty(value = "codigo")
    lateinit var codigoErro: String

    @JsonProperty(value = "mensagem")
    var mensagem: String? = ""

    @JsonProperty(value = "microservico")
    var microservice: EMicroservice = EMicroservice.CARDAPIO

    constructor()

    constructor(codigo: String, mensagem: String?) {
        this.codigoErro = codigo
        this.mensagem = mensagem
    }
}