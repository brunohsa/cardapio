package br.com.unip.cardapio.security.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Claims(val scopes: List<String>, val email: String)