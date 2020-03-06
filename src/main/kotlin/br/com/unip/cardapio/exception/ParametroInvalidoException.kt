package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.PARAMETRO_INVALIDO
import org.springframework.http.HttpStatus.BAD_REQUEST

class ParametroInvalidoException : CardapioBaseException {

    constructor() : this(PARAMETRO_INVALIDO)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}