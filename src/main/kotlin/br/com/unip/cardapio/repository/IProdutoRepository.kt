package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Produto
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface IProdutoRepository : MongoRepository<Produto, String> {

    fun findByIdAndCategoriaId(id: String, categoriaId: String): Optional<Produto>
}