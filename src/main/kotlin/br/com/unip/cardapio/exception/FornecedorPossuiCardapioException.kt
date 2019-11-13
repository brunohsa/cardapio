package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus

class FornecedorPossuiCardapioException : CadastroException {

    constructor() : super(ECodigoErro.CAD018, HttpStatus.BAD_REQUEST, "Fornecedor já possui um cardápio cadastrado")
}