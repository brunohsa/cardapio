package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Cardapio
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface ICardapioRepository : MongoRepository<Cardapio, String> {

    fun findByUuidFornecedor(uuidFornecedor: String): List<Cardapio>

    fun findByAtivo(ativo: Boolean = true): Optional<Cardapio>

    fun findByUuidFornecedorAndAtivo(uuidFornecedor: String, ativo: Boolean = true): Optional<Cardapio>

    fun countByUuidFornecedor(uuidFornecedor: String): Int
}