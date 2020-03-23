package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro.CAMPO_NOME_OBRIGATORIO

class Nome : ICampo<String> {

    val nome: String

    constructor(nome: String?) {
        try {
            this.nome = CampoObrigatorio(nome).get()
        } catch (e: CampoObrigatorioException) {
            throw CampoObrigatorioException(CAMPO_NOME_OBRIGATORIO)
        }
    }

    override fun get(): String {
        return nome
    }
}