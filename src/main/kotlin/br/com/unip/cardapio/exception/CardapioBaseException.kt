package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class CardapioBaseException : RuntimeException {

    var codigoErro: ECodigoErro

    var httpStatus: HttpStatus

    constructor(codigoErro: ECodigoErro,
                httpStatus: HttpStatus) {
        this.codigoErro = codigoErro
        this.httpStatus = httpStatus
    }
}
