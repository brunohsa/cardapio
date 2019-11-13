package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CadastroDTO

interface IAutenticacaoService {

    fun buscarCadastroPorEmail(email: String): CadastroDTO
}