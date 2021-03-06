package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.CampoNumericoException
import br.com.unip.cardapio.exception.CampoObrigatorioException
import br.com.unip.cardapio.exception.ECodigoErro.CAMPO_VALOR_DEVE_SER_NUMERICO
import br.com.unip.cardapio.exception.ECodigoErro.CAMPO_VALOR_OBRIGATORIO

class Valor : ICampo<Double> {

    val valor: Double

    constructor(valor: String?) {
        try {
            this.valor = CampoMonetario(CampoObrigatorio(valor)).get()
        } catch (e: CampoObrigatorioException) {
            throw CampoObrigatorioException(CAMPO_VALOR_OBRIGATORIO)
        } catch (e: CampoNumericoException) {
            throw CampoNumericoException(CAMPO_VALOR_DEVE_SER_NUMERICO)
        }
    }

    override fun get(): Double {
        return valor
    }
}