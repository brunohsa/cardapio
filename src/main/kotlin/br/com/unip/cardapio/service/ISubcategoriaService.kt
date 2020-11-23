package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.SubcategoriaDTO
import br.com.unip.cardapio.repository.entity.Subcategoria

interface ISubcategoriaService {

    fun buscar(id: String): Subcategoria

    fun buscarTodas(): List<SubcategoriaDTO>
}