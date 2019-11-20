package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus

class CampoObrigatorioException : CadastroException {


    constructor() : super(ECodigoErro.CAD019, HttpStatus.BAD_REQUEST)

    constructor(mensagem: String) : this(mensagem, ECodigoErro.CAD019)

    constructor(mensagem: String, codigoErro: ECodigoErro) : super(codigoErro, HttpStatus.BAD_REQUEST, mensagem)
}