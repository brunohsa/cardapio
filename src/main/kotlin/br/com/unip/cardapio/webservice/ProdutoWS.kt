package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.service.IProdutoService
import br.com.unip.cardapio.webservice.model.request.AlterarImagemProdutoRequest
import br.com.unip.cardapio.webservice.model.response.ProdutoResponse
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/produtos"])
class ProdutoWS(val produtoService: IProdutoService) {

    @RequestMapping(value = ["/{id_produto}/cardapio/{id_cardapio}/alterar-imagem"], method = [RequestMethod.PUT])
    fun alterarImagem(@PathVariable(value = "id_produto") idProduto: String?,
                      @PathVariable(value = "id_cardapio") idCardapio: String?,
                      @RequestBody request: AlterarImagemProdutoRequest): ResponseEntity<Void> {

        produtoService.alterarImagem(idCardapio, idProduto, request.imagem!!)
        return ResponseEntity.ok().build()
    }

    @RequestMapping(value = ["/{id_produto}/imagem/download"], method = [RequestMethod.GET])
    fun downloadImagem(@PathVariable(value = "id_produto") idProduto: String?): ResponseEntity<InputStreamResource> {
        var imagem = produtoService.downloadImagem(idProduto)
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagem)
    }

    @RequestMapping(value = ["/{id_produto}"], method = [RequestMethod.GET])
    fun buscarProduto(@PathVariable(value = "id_produto") idProduto: String?): ResponseEntity<ProdutoResponse> {
        var produto = produtoService.buscarProduto(idProduto)
        var response = ProdutoResponse(produto.id, produto.nome, produto.descricao, produto.valor, produto.categoria)
        return ResponseEntity.ok().body(response)
    }
}