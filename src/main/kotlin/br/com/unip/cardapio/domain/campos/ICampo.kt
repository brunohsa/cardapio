package br.com.unip.cardapio.domain.campos

interface ICampo<T> {

    fun get(): T
}