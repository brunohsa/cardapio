package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import org.springframework.http.HttpStatus.NOT_FOUND

class ProdutoNaoEncontradoException : CardapioBaseException {

    constructor() : super(PRODUTO_NAO_ENCONTRADO, NOT_FOUND)
}