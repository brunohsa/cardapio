package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Subcategoria
import org.springframework.data.mongodb.repository.MongoRepository

interface ISubcategoriaRepository : MongoRepository<Subcategoria, String> {
}