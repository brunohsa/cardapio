package br.com.unip.cardapio.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class CarrinhoDTO {

    @JsonProperty(value = "id")
    lateinit var id: String

    @JsonProperty(value = "valor_total")
    lateinit var valorTotal: BigDecimal

    @JsonProperty(value = "data_criacao")
    lateinit var dataCriacao: String

    constructor()

    constructor(id: String, valorTotal: BigDecimal, dataCriacao: String) {
        this.id = id
        this.valorTotal = valorTotal
        this.dataCriacao = dataCriacao
    }
}