package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Cardapio
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface ICardapioRepository : MongoRepository<Cardapio, String> {

    fun findByUuidFornecedor(uuidFornecedor: String): List<Cardapio>

    fun findByAtivo(ativo: Boolean = true): Optional<Cardapio>

    @Query(value = "{ 'uuidFornecedor' : {'\$in' : ?0 }, 'ativo' : true }")
    fun buscarCardapiosAtivosPorFornecedoresUUID(fornecedoresIds: List<String>): List<Cardapio>
}