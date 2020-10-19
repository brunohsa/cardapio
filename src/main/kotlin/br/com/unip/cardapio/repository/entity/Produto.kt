package br.com.unip.cardapio.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "produto")
class Produto {

    @Id
    var id: String? = null

    var nome: String? = null

    var descricao: String? = null

    var valor: BigDecimal? = null

    var urlImagem: String? = null

    var categoriaId: String? = null

    var cardapioId: String? = null

    var estoque: Int? = null

    constructor()

    constructor(nome: String?, descricao: String?, valor: BigDecimal?, categoriaId: String?, cardapioId: String?,
                estoque: Int?) {
        this.nome = nome
        this.descricao = descricao
        this.valor = valor
        this.urlImagem = urlImagem
        this.categoriaId = categoriaId
        this.cardapioId = cardapioId
        this.estoque = estoque
    }
}