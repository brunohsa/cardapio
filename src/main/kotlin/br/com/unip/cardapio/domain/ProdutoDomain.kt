package br.com.unip.cardapio.domain

import br.com.unip.cardapio.domain.campos.Categoria
import br.com.unip.cardapio.domain.campos.Descricao
import br.com.unip.cardapio.domain.campos.Imagem
import br.com.unip.cardapio.domain.campos.Nome
import br.com.unip.cardapio.domain.campos.Valor

class ProdutoDomain {

    val nome: Nome

    val descricao: Descricao

    val valor: Valor

    val categoria: Categoria

    val imagem: Imagem

    constructor(nome: String?,
                descricao: String?,
                valor: String?,
                categoria: String?,
                imagem: String?) {
        this.nome = Nome(nome)
        this.descricao = Descricao(descricao)
        this.valor = Valor(valor)
        this.categoria = Categoria(categoria)
        this.imagem = Imagem(imagem)
    }
}