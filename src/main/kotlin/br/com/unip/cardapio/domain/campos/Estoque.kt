package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.CampoEstoqueNaoPodeSerNegativoException

class Estoque : ICampo<Int?> {

    val valor: Int?

    constructor(valor: Int?) {
        this.validar()
        this.valor = CampoOpcional(valor).get()
    }

    private fun validar() {
        if(valor != null && valor < 0) {
            throw CampoEstoqueNaoPodeSerNegativoException();
        }
    }

    override fun get(): Int? {
        return valor
    }
}