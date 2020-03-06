package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.ERRO_INESPERADO
import br.com.unip.cardapio.webservice.model.response.Erro
import br.com.unip.cardapio.webservice.model.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HandlerException {

    @ExceptionHandler(CardapioBaseException::class)
    fun handlerCadastroException(e: CardapioBaseException): ResponseEntity<Response> {
        val erro = Erro(e.codigoErro, e.message ?: "")
        return ResponseEntity.status(e.httpStatus.value()).body(Response(erro))
    }

    @ExceptionHandler(Throwable::class)
    fun handlerErroInesperado(e: Throwable): ResponseEntity<Response> {
        val erro = Erro(ERRO_INESPERADO, "Erro inesperado")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response(erro))
    }
}