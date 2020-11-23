package br.com.unip.cardapio.webservice.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CategoriaResponse {

    @JsonProperty(value = "id")
    var id: String?

    @JsonProperty(value = "titulo")
    var titulo: String?

    @JsonProperty(value = "subcategoria")
    var subcategoria: SubcategoriaResponse?

    @JsonProperty(value = "produtos")
    var produtos: List<ProdutoResponse>? = emptyList()

    constructor(id: String?, titulo: String?, subcategoria: SubcategoriaResponse?, produtos: List<ProdutoResponse>?) {
        this.id = id
        this.titulo = titulo
        this.subcategoria = subcategoria
        this.produtos = produtos
    }
}