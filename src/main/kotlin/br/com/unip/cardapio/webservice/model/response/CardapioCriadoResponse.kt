package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CardapioCriadoResponse(@JsonProperty(value = "cardapio_id") val id: String?)