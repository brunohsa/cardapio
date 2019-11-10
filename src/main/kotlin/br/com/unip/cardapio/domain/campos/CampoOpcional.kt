package br.com.unip.cardapio.domain.campos

class CampoOpcional<T> : ICampo<T> {

    val valor: T

    constructor(valor: T) {
        this.valor = valor
    }

    override fun get(): T {
        return valor
    }
}