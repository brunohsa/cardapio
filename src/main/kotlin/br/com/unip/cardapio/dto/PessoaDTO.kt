package br.com.unip.cardapio.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class PessoaDTO(@JsonProperty(value = "nome") val nome: String,
                @JsonProperty(value = "documento") var documento: DocumentoDTO?)
