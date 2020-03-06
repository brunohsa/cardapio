package br.com.unip.cardapio.security.filter

import br.com.unip.cardapio.exception.ECodigoErro.ERRO_INESPERADO
import br.com.unip.cardapio.security.util.TokenUtil
import br.com.unip.cardapio.webservice.model.response.Erro
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthenticationFilter(val tokenUtil: TokenUtil) : GenericFilterBean() {


    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        try {
            val authentication = tokenUtil.getAuthentication(request as HttpServletRequest)
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (e: RuntimeException) {
            setJWTErrorResponse(response, HttpStatus.BAD_REQUEST, e.cause.toString(), e.message!!)
        } catch (e: RuntimeException) {
            setJWTErrorResponse(response, HttpStatus.UNAUTHORIZED, e.cause.toString(), e.message!!)
        }
    }

    @Throws(IOException::class)
    private fun setJWTErrorResponse(response: ServletResponse, httpStatus: HttpStatus, cause: String, description: String) {
        val httpResponse = response as HttpServletResponse

        val error = Erro(ERRO_INESPERADO, description)
        httpResponse.writer.write(ObjectMapper().writeValueAsString(error))
        httpResponse.status = httpStatus.value()
        httpResponse.contentType = MediaType.APPLICATION_JSON_VALUE
    }
}