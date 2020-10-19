package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.AlterarCategoriaDTO
import br.com.unip.cardapio.dto.InserirCategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.entity.Categoria

interface ICategoriaService {

    fun adicionar(dto: InserirCategoriaDTO, cardapioId: String): Categoria

    fun alterar(dto: AlterarCategoriaDTO, cardapioId: String)

    fun remover(categoriaId: String, cardapioId: String)

    fun adicionarProduto(categoriaId: String, cardapioId: String, produtoDTO: ProdutoDTO): Categoria

    fun removerProduto(categoriaId: String, cardapioId: String, produtoId: String)

    fun buscar(id: String, cardapioId: String): Categoria
}