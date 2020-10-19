package br.com.unip.cardapio.dto

class InserirCategoriaDTO(var titulo: String? = "",
                          var subcategoriaId: String?,
                          var produtos: List<ProdutoDTO>? = emptyList())