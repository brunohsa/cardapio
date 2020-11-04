package br.com.unip.cardapio.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FiltroProdutosDTO(val notaApartirDe: String?,
                             val notaMenorQue: String?,
                             val precoApartirDe: String?,
                             val precoMenorQue: String?,
                             val nome: String?,
                             val subcategoriaId: String?,
                             val tipoOrdenacao: String?,
                             val campoOrdenacao: String?,
                             val limite: Int?)