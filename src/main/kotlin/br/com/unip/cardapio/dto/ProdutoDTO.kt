package br.com.unip.cardapio.dto

class ProdutoDTO {

    var produtoId: String? = ""
    var cardapioId: String? = ""
    var nome: String?
    var descricao: String?
    var valor: String?
    var estoque: Int? = 0
    var urlImagem: String? = ""
    var vendidos: Int? = 0
    var nota: Double? = 0.0

    constructor(nome: String?, descricao: String?, valor: String?, estoque: Int?) {
        this.nome = nome
        this.descricao = descricao
        this.valor = valor
        this.estoque = estoque
    }

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, estoque: Int?) :
            this(nome, descricao, valor, estoque) {
        this.produtoId = id
        this.nota = nota
    }

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, estoque: Int?, urlImagem: String?) :
            this(id, nome, descricao, valor, estoque) {
        this.urlImagem = urlImagem
    }

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, estoque: Int?, vendidos: Int?,
                nota: Double?, urlImagem: String?, cardapioId: String?) : this(nome, descricao, valor, estoque) {
        this.cardapioId = cardapioId
        this.produtoId = id
        this.vendidos = vendidos
        this.nota = nota
        this.urlImagem = urlImagem
    }
}