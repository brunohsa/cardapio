package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus

class ProdutoNaoEncontradoException : CadastroException {

    constructor() : super(ECodigoErro.CAD020, HttpStatus.NOT_FOUND, "Produto não encontrado.")
}