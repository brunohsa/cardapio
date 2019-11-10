package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.entity.Produto

interface IProdutoService {

    fun cadastrar(uuidFornecedor: String?, dto: ProdutoDTO): Produto
}