package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.CampoNumericoException

class CampoMonetario : ICampo<Double> {

    private val REGEX_NUMEROS: Regex = "-?\\d+(\\.\\d+)?".toRegex()
    private val valor: Double

    constructor(valor: ICampo<String>) {
        if (!valor.get().matches(REGEX_NUMEROS)) {
            throw CampoNumericoException()
        }
        this.valor = valor.get().toDouble()
    }

    override fun get(): Double {
        return valor
    }
}