package br.com.unip.cardapio.webservice.model.response

import br.com.unip.cardapio.exception.ECodigoErro
import com.fasterxml.jackson.annotation.JsonProperty

class Erro(@JsonProperty("codigo") val codigoErro: ECodigoErro,
           @JsonProperty("mensagem") val mensagem: String)