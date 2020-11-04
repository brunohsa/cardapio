package br.com.unip.cardapio.dto

class ProdutoDTO {

    var produtoId: String? = ""
    var nome: String?
    var descricao: String?
    var valor: String?
    var estoque: Int? = 0
    var urlImagem: String? = ""
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

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, estoque: Int?, urlImagem: String?, nota: Double?) :
            this(id, nome, descricao, valor, estoque, nota) {
        this.urlImagem = urlImagem
    }

    constructor(id: String?, nome: String?, descricao: String?, valor: String?, estoque: Int?, nota: Double?) :
            this(nome, descricao, valor, estoque) {
        this.produtoId = id
        this.nota = nota
    }
}