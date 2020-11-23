package br.com.unip.cardapio.service

interface ILocalizacaoService {

    fun buscarFornecedoresUUIDPorLocalizacao(latitude: Double, longitude: Double): List<String>
}