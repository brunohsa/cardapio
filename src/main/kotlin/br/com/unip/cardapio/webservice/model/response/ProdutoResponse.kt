package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProdutoResponse(@JsonProperty(value = "id")
                      var id: String?,
                      @JsonProperty(value = "nome")
                      var nome: String?,
                      @JsonProperty(value = "descricao")
                      var descricao: String?,
                      @JsonProperty(value = "valor")
                      var valor: String?,
                      @JsonProperty(value = "estoque")
                      var estoque: Int? = 0,
                      @JsonProperty(value = "nota")
                      var nota: Double? = null,
                      @JsonProperty(value = "url")
                      var url: String? = "")