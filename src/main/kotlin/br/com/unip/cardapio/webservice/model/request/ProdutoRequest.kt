package br.com.unip.cardapio.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class ProdutoRequest(@JsonProperty("nome") val nome: String?,
                     @JsonProperty("descricao") val descricao: String?,
                     @JsonProperty("valor") val valor: String?,
                     @JsonProperty("categoria") val categoria: String?,
                     @JsonProperty("imagem") val imagem: String?)