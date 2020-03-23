package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.security.Permissoes.ALTERAR_IMAGEM_PRODUTO
import br.com.unip.cardapio.security.Permissoes.BUSCAR_PRODUTO
import br.com.unip.cardapio.service.IProdutoService
import br.com.unip.cardapio.webservice.model.request.AlterarImagemProdutoRequest
import br.com.unip.cardapio.webservice.model.response.ProdutoResponse
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/produtos"])
class ProdutoWS(val produtoService: IProdutoService) {

    @PutMapping(value = ["/{id_produto}/alterar-imagem"])
    @PreAuthorize("hasAuthority('$ALTERAR_IMAGEM_PRODUTO')")
    fun alterarImagem(@PathVariable(value = "id_produto") idProduto: String?,
                      @RequestBody request: AlterarImagemProdutoRequest): ResponseEntity<Void> {

        produtoService.alterarImagem(idProduto, request.imagem!!)
        return ResponseEntity.ok().build()
    }

    @GetMapping(value = ["/{id_produto}/imagem/download"])
    fun downloadImagem(@PathVariable(value = "id_produto") idProduto: String?): ResponseEntity<InputStreamResource> {
        var imagem = produtoService.downloadImagem(idProduto)
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagem)
    }

    @GetMapping(value = ["/{id_produto}"])
    @PreAuthorize("hasAuthority('$BUSCAR_PRODUTO')")
    fun buscarProduto(@PathVariable(value = "id_produto") idProduto: String?): ResponseEntity<ProdutoResponse> {
        var produto = produtoService.buscar(idProduto)
        var response = ProdutoResponse(produto.id, produto.nome, produto.descricao, produto.valor)
        return ResponseEntity.ok().body(response)
    }
}