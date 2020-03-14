package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Categoria
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface ICategoriaRepository : MongoRepository<Categoria, String> {

    fun findByIdAndCardapioId(id: String, cardapioId: String): Optional<Categoria>
}