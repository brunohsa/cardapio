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

    var valor: Double? = null

    var urlImagem: String? = null

    var categoriaId: String? = null

    var subcategoriaId: String? = null

    var cardapioId: String? = null

    var estoque: Int? = null

    var vendidos: Int = 0

    var nota: Double? = null

    constructor()

    constructor(nome: String?, descricao: String?, valor: Double?, categoriaId: String?, cardapioId: String?,
                subcategoriaId: String?, estoque: Int?) {
        this.nome = nome
        this.descricao = descricao
        this.valor = valor
        this.urlImagem = urlImagem
        this.subcategoriaId = subcategoriaId
        this.categoriaId = categoriaId
        this.cardapioId = cardapioId
        this.estoque = estoque
    }
}