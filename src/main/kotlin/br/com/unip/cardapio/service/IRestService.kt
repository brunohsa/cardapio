package br.com.unip.cardapio.service

import org.springframework.util.LinkedMultiValueMap
import kotlin.reflect.KClass

interface IRestService {

    fun <T : Any> post(uri: String, request: Any, response: KClass<T>): T

    fun <T : Any> post(uri: String, request: Any, headers: LinkedMultiValueMap<String, String>, response: KClass<T>): T

    fun post(uri: String, request: Any): String

    fun post(uri: String, request: Any, headers: LinkedMultiValueMap<String, String>): String

    fun <T : Any> get(uri: String, response: KClass<T>): T

    fun <T : Any> get(uri: String, response: KClass<T>, headers: LinkedMultiValueMap<String, String>): T


}