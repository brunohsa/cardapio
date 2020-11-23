package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro.CAMPO_DESCRICAO_OBRIGATORIO

class Descricao : ICampo<String?> {

    val valor: String?

    constructor(valor: String?) {
        try {
            this.valor = CampoOpcional(valor).get()
        } catch (e: CampoObrigatorioException) {
            throw CampoObrigatorioException(CAMPO_DESCRICAO_OBRIGATORIO)
        }
    }

    override fun get(): String? {
        return valor
    }
}