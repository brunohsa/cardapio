package br.com.unip.cardapio.repository.entity

import br.com.unip.cardapio.repository.entity.enums.ECategoriaProduto
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

    var categoria: ECategoriaProduto? = null

    var urlImagem: String? = null

    constructor()

    constructor(nome: String?, descricao: String?, valor: BigDecimal?, categoria: ECategoriaProduto?, urlImagem: String?) {
        this.nome = nome
        this.descricao = descricao
        this.valor = valor
        this.categoria = categoria
        this.urlImagem = urlImagem
    }
}