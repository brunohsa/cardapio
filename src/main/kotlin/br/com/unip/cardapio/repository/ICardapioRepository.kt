package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Cardapio
import org.springframework.data.mongodb.repository.MongoRepository

interface ICardapioRepository : MongoRepository<Cardapio, String> {

    fun findByUuidFornecedor(uuidFornecedor: String): Cardapio?
}