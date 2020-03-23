package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus

class FornecedorNaoPossuiCardapioException : CadastroException {

    constructor() : super(ECodigoErro.CAD021, HttpStatus.NOT_FOUND, "Fornecedor não possui nenhum cardápio cadastrado")
}