package br.com.unip.cardapio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StartApplication

fun main(args: Array<String>) {
    runApplication<StartApplication>(*args)
}