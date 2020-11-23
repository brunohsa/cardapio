package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus.BAD_REQUEST

class CampoEstoqueNaoPodeSerNegativoException : CardapioBaseException {

    constructor() : super(ECodigoErro.CAMPO_ESTOQUE_NAO_PODE_SER_NEGATIVO, BAD_REQUEST)
}