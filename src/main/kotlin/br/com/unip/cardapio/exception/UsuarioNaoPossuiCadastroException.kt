package br.com.unip.cardapio.exception

import br.com.unip.cardapio.exception.ECodigoErro.USUARIO_NAO_POSSUI_CADASTRO
import org.springframework.http.HttpStatus.BAD_REQUEST

class UsuarioNaoPossuiCadastroException : CardapioBaseException {

    constructor() : super(USUARIO_NAO_POSSUI_CADASTRO, BAD_REQUEST)
}