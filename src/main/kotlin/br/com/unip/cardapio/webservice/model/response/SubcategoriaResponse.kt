package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class SubcategoriaResponse(@JsonProperty(value = "id")
                           val id: String?,
                           @JsonProperty(value = "descricao")
                           val descricao: String?,
                           @JsonProperty(value = "nome")
                           val nome: String? = "")