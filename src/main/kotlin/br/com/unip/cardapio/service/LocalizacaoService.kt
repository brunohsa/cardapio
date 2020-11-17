package br.com.unip.cardapio.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.util.LinkedMultiValueMap

@Repository
class LocalizacaoService(val restService: IRestService) : ILocalizacaoService {

    @Value("\${carrinho.apikey}")
    private val APIKEY_CARRINHO = ""

    @Value("\${microservice.localizacao.url}")
    private val LOCALIZACAO_URL = ""

    private val APIKEY_TAG = "key"

    private val FORNECEDORES_URL = "v1/fornecedores"

    override fun buscarFornecedoresUUIDPorLocalizacao(latitude: Double, longitude: Double): List<String> {
        val headers = getHeaderComApiKey()
        val url = "$LOCALIZACAO_URL$FORNECEDORES_URL/latitude/$latitude/longitude/$longitude/cadastros-uuid"
        val response = restService.get(url, String::class, headers)

        return ObjectMapper().readValue(response, object : TypeReference<List<String>>() {})
    }

    private fun getHeaderComApiKey(): LinkedMultiValueMap<String, String> {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(APIKEY_TAG, APIKEY_CARRINHO)

        return headers
    }
}