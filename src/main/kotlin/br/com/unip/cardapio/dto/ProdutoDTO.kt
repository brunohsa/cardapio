package br.com.unip.cardapio.dto

class ProdutoDTO {

    var id: String? = ""
    var nome: String?
    var descricao: String?
    var valor: String?
    var categoria: String?
    var imagem: String?

    constructor(nome: String?, descricao: String?, valor: String?, categoria: String?, imagem: String?) {
        this.nome = nome
        this.descricao = descricao
        this.valor = valor
        this.categoria = categoria
        this.imagem = imagem
    }

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, categoria: String?, imagem: String?) :
            this(nome, descricao, valor, categoria, imagem) {
        this.id = id
    }


}