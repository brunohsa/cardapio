package br.com.unip.cardapio.security.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class DadosToken(val claims: Claims, val exp: Long)