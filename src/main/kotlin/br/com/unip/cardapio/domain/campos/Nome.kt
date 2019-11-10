package br.com.unip.cardapio.domain.campos

class Nome : ICampo<String> {

    val nome: String

    constructor(nome: String?) {
        this.nome = CampoObrigatorio(nome).get()
    }

    override fun get(): String {
        return nome
    }
}