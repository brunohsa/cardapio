package br.com.unip.cardapio.security.util

import br.com.unip.cardapio.security.dto.DadosToken
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.Base64
import javax.servlet.http.HttpServletRequest


@Component
class TokenUtil(val mapper: ObjectMapper) {

    private val TOKEN_TAG = "token"

    @Throws(RuntimeException::class)
    fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val token = request.getHeader(TOKEN_TAG)
        if (token.isNullOrEmpty()) {
            throw RuntimeException()
        }
        val splitString = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val base64EncodedBody = splitString[1]

        val decoder = Base64.getDecoder()
        val body = String(decoder.decode(base64EncodedBody))

        val dadosToken = mapper.readValue(body, DadosToken::class.java)
        this.verificarSeTokenExpirado(dadosToken)

        val autorizacoes = this.getAutorizacoes(dadosToken)
        return UsernamePasswordAuthenticationToken(dadosToken.claims.email, null, autorizacoes)
    }

    private fun verificarSeTokenExpirado(dadosToken: DadosToken) {
        val time = dadosToken.exp * 1000
        var dateExpiracao = Timestamp(time).toLocalDateTime()
        if (LocalDateTime.now().isAfter(dateExpiracao)) {
            throw RuntimeException()
        }
    }

    private fun getAutorizacoes(dadosToken: DadosToken): List<SimpleGrantedAuthority> {
        val scopes = dadosToken.claims.scopes
        return scopes.map { s -> SimpleGrantedAuthority(s) }.toList()
    }
}