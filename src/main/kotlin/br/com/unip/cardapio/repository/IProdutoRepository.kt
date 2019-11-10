package br.com.unip.cardapio.repository

import br.com.unip.cardapio.repository.entity.Produto
import org.springframework.data.mongodb.repository.MongoRepository

interface IProdutoRepository : MongoRepository<Produto, String>