package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CarrinhoDTO

interface ICarrinhoService {

    fun buscarCarrinhosPorFornecedor(fornecedorUUID: String): List<CarrinhoDTO>
}