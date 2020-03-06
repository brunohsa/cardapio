package br.com.unip.cardapio.repository.persistence

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD

@Retention(RUNTIME)
@Target(FIELD)
annotation class CascadeSave