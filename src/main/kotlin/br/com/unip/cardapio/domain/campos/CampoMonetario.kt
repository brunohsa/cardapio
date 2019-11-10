package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.CampoNumericoException
import java.math.BigDecimal

class CampoMonetario : ICampo<BigDecimal> {

    private val REGEX_NUMEROS: Regex = "-?\\d+(\\.\\d+)?".toRegex()
    private val valor: BigDecimal

    constructor(valor: ICampo<String>) {
        if (!valor.get().matches(REGEX_NUMEROS)) {
            throw CampoNumericoException()
        }
        this.valor = BigDecimal(valor.get())
    }

    override fun get(): BigDecimal {
        return valor
    }
}