package br.com.unip.cardapio.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class DocumentoDTO(@JsonProperty(value = "tipo") var tipo: String,
                   @JsonProperty(value = "numero") var numero: String)