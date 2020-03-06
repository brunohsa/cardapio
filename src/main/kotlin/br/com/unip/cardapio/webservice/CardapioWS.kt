package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.service.ICardapioService
import br.com.unip.cardapio.webservice.model.request.CardapioRequest
import br.com.unip.cardapio.webservice.model.request.ProdutoRequest
import br.com.unip.cardapio.webservice.model.response.CardapioCriadoResponse
import br.com.unip.cardapio.webservice.model.response.CardapioResponse
import br.com.unip.cardapio.webservice.model.response.ProdutoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/cardapio"])
class CardapioWS(val cardapioService: ICardapioService) {

    @RequestMapping(value = ["/criar"], method = [RequestMethod.POST])
    fun criarCardapio(@RequestBody request: CardapioRequest): ResponseEntity<CardapioCriadoResponse> {
        val dto = CardapioDTO(request.nome)
        val id = cardapioService.criar(dto)

        return ResponseEntity.ok(CardapioCriadoResponse(id))
    }

    @RequestMapping(value = ["/{id_cardapio}/produto/{id_produto}/alterar"], method = [RequestMethod.PUT])
    fun alterarProduto(@PathVariable(value = "id_cardapio") idCardapio: String,
                       @PathVariable(value = "id_produto") idProduto: String,
                       @RequestBody request: ProdutoRequest): ResponseEntity<Void> {
        val produto = ProdutoDTO(idProduto,
                request.nome,
                request.descricao,
                request.valor,
                request.categoria,
                request.imagem
        )
        cardapioService.alterarProduto(idCardapio, idProduto, produto)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/{id_cardapio}/adicionar-produto"], method = [RequestMethod.PUT])
    fun adicionarProduto(@PathVariable(value = "id_cardapio") idCardapio: String,
                         @RequestBody request: ProdutoRequest): ResponseEntity<CardapioResponse> {
        val produto = ProdutoDTO(request.nome, request.descricao, request.valor, request.categoria, request.imagem)
        val dto = cardapioService.adicionarProduto(idCardapio, produto)

        val response = this.map(dto)!!
        return ResponseEntity.ok(response)
    }

    @RequestMapping(value = ["/{id_cardapio}/produto/{id_produto}"], method = [RequestMethod.DELETE])
    fun removerProduto(@PathVariable(value = "id_cardapio") idCardapio: String?,
                       @PathVariable(value = "id_produto") idProduto: String?): ResponseEntity<Void> {
        cardapioService.removerProduto(idCardapio, idProduto)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/buscar"], method = [RequestMethod.GET])
    fun buscarCardapio(): ResponseEntity<CardapioResponse> {
        val dto = cardapioService.buscar()

        val response = this.map(dto)
        return if (response != null) ResponseEntity.ok(response) else ResponseEntity.notFound().build()
    }

    private fun map(cardapioDTO: CardapioDTO?): CardapioResponse? {
        if (cardapioDTO == null) {
            return null
        }
        var produtosResponse = this.map(cardapioDTO.produtos!!)
        return CardapioResponse(cardapioDTO.id, cardapioDTO.titulo, produtosResponse)
    }

    private fun map(produtosDTO: List<ProdutoDTO>): List<ProdutoResponse> {
        return produtosDTO.map { p ->
            ProdutoResponse(p.id, p.nome, p.descricao, p.valor, p.categoria)
        }.toList()
    }
}