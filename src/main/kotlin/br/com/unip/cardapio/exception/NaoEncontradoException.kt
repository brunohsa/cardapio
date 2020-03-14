package br.com.unip.cardapio.exception

import org.springframework.http.HttpStatus.NOT_FOUND

class NaoEncontradoException : CardapioBaseException {

    constructor(erro: ECodigoErro) : super(erro, NOT_FOUND)
}