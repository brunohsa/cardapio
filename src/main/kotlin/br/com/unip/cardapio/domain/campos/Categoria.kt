package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.repository.entity.enums.ECategoriaProduto

class Categoria : ICampo<ECategoriaProduto> {

    val valor: ECategoriaProduto

    constructor(valor: String?) {
        this.valor = ECategoriaProduto.valueOf(CampoObrigatorio(valor).get())
    }

    override fun get(): ECategoriaProduto {
        return valor
    }
}