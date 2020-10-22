package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CarrinhoDTO
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.util.LinkedMultiValueMap

@Repository
class CarrinhoService(val restService: IRestService) : ICarrinhoService {

    @Value("\${carrinho.apikey}")
    private val APIKEY_CARRINHO = ""

    @Value("\${microservice.carrinho.url}")
    private val CARRINHO_URL = ""

    private val APIKEY_TAG = "key"

    private val CARRINHOS_URL = "v1/carrinhos"

    override fun buscarCarrinhosPorFornecedor(fornecedorUUID: String): List<CarrinhoDTO> {
        val headers = getHeaderComApiKey()
        val response = restService.get("$CARRINHO_URL$CARRINHOS_URL/cardapio/fornecedor/$fornecedorUUID",
                String::class, headers)

        return ObjectMapper().readValue(response, object : TypeReference<List<CarrinhoDTO>>() {})
    }

    private fun getHeaderComApiKey(): LinkedMultiValueMap<String, String> {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(APIKEY_TAG, APIKEY_CARRINHO)

        return headers
    }
}