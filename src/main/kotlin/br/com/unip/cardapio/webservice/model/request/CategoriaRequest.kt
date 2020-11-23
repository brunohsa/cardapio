package br.com.unip.cardapio.webservice.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CategoriaRequest(@JsonProperty("titulo") val titulo: String?,
                       @JsonProperty("subcategoria_id") val subcategoriaId: String?)