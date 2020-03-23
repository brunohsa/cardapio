package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.CAMPO_DEVE_SER_NUMERICO
import org.springframework.http.HttpStatus.BAD_REQUEST

class CampoNumericoException : CardapioBaseException {

    constructor() : this(CAMPO_DEVE_SER_NUMERICO)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}