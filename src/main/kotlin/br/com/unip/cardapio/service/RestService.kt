package br.com.unip.cardapio.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import kotlin.reflect.KClass


@Repository
class RestService(val mapper: ObjectMapper) : IRestService {

    private val restTemplate = RestTemplate()

    override fun <T : Any> post(uri: String, request: Any, response: KClass<T>): T {
        return post(uri, request, LinkedMultiValueMap(), response)
    }

    override fun <T : Any> post(uri: String, request: Any, headers: LinkedMultiValueMap<String, String>, response: KClass<T>): T {
        val res = post(uri, request, headers)
        return mapper.readValue(res, response.java)
    }

    override fun post(uri: String, request: Any): String {
        return post(uri, request, LinkedMultiValueMap())
    }

    override fun post(uri: String, request: Any, headers: LinkedMultiValueMap<String, String>): String {
        val requestJson = mapper.writeValueAsString(request)

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.addAll(headers)

        val entity = HttpEntity(requestJson, httpHeaders)
        val res = restTemplate.postForEntity<String>(uri, entity, String::class)
        return res.body!!
    }

    override fun <T : Any> get(uri: String, response: KClass<T>): T {
        return get(uri, response,  LinkedMultiValueMap())
    }

    override fun <T : Any> get(uri: String, response: KClass<T>, headers: LinkedMultiValueMap<String, String>): T {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.addAll(httpHeaders)

        val entity = HttpEntity(null, headers)
        return restTemplate.exchange(uri, GET, entity, response.java).body!!
    }
}