package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus.BAD_REQUEST

class PossuiItensDoCardapioEmCarrinhosException : CardapioBaseException {

    constructor() : super(ECodigoErro.POSSUI_ITENS_DO_CARDAPIO_EM_CARRINHOS, BAD_REQUEST)
}