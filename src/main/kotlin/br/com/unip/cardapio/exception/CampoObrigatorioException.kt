package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.CAMPO_OBRIGATORIO
import org.springframework.http.HttpStatus.BAD_REQUEST

class CampoObrigatorioException : CardapioBaseException {

    constructor() : this(CAMPO_OBRIGATORIO)

    constructor(codigoErro: ECodigoErro) : super(codigoErro, BAD_REQUEST)
}