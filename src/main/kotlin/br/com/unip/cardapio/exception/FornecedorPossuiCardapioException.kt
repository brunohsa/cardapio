package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.POSSUI_CARDAPIO_CADASTRADO
import org.springframework.http.HttpStatus.BAD_REQUEST

class FornecedorPossuiCardapioException : CardapioBaseException {

    constructor() : super(POSSUI_CARDAPIO_CADASTRADO, BAD_REQUEST)
}