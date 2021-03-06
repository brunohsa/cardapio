package br.com.unip.cardapio.domain.campos

class Imagem : ICampo<String?> {

    val valor: String?

    constructor(valor: String?) {
        this.valor = CampoOpcional(valor).get()
    }

    override fun get(): String? {
        return valor
    }
}