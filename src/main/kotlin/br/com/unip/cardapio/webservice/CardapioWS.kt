package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.security.Permissoes.ALTERAR_CARDAPIO
import br.com.unip.cardapio.security.Permissoes.BUSCAR_CARDAPIO
import br.com.unip.cardapio.security.Permissoes.CRIAR_CARDAPIO
import br.com.unip.cardapio.service.ICardapioService
import br.com.unip.cardapio.webservice.model.request.CardapioRequest
import br.com.unip.cardapio.webservice.model.request.CategoriaRequest
import br.com.unip.cardapio.webservice.model.request.ProdutoRequest
import br.com.unip.cardapio.webservice.model.response.CardapioCriadoResponse
import br.com.unip.cardapio.webservice.model.response.CardapioResponse
import br.com.unip.cardapio.webservice.model.response.CategoriaResponse
import br.com.unip.cardapio.webservice.model.response.ProdutoResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/cardapios"])
class CardapioWS(val cardapioService: ICardapioService) {

    @PostMapping(value = ["/criar"])
    //@PreAuthorize("hasAuthority('$CRIAR_CARDAPIO')")
    fun criarCardapio(@RequestBody request: CardapioRequest): ResponseEntity<CardapioCriadoResponse> {
        val dto = CardapioDTO(request.nome)
        val id = cardapioService.criar(dto)

        return ResponseEntity.ok(CardapioCriadoResponse(id))
    }

    @GetMapping(value = ["/buscar"])
    //@PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarCardapio(): ResponseEntity<CardapioResponse> {
        val dto = cardapioService.buscar()
        return ResponseEntity.ok(this.map(dto))
    }

    @PutMapping(value = ["/adicionar-categoria"])
    //@PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun adicionarCategoria(@RequestBody request: CategoriaRequest): ResponseEntity<CardapioResponse> {
        val categoria = CategoriaDTO(request.titulo)
        val cardapio = cardapioService.adicionarCategoria(categoria)
        return ResponseEntity.ok(this.map(cardapio))
    }

    @DeleteMapping(value = ["/categoria/{id_categoria}"])
    //@PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun removerCategoria(@PathVariable(value = "id_categoria") idCategoria: String): ResponseEntity<Void> {
        cardapioService.removerCategoria(idCategoria)
        return ResponseEntity.ok().build()
    }

    @PutMapping(value = ["/categoria/{id_categoria}/adicionar-produto"])
   // @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun adicionarProduto(@PathVariable(value = "id_categoria") idCategoria: String,
                         @RequestBody request: ProdutoRequest): ResponseEntity<Void> {
        val produto = ProdutoDTO(request.nome, request.descricao, request.valor, request.imagem)
        cardapioService.adicionarProduto(idCategoria, produto)

        return ResponseEntity.ok().build()
    }

    @PutMapping(value = ["/categoria/{id_categoria}/produto/{id_produto}/alterar"])
   // @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun alterarProduto(@PathVariable(value = "id_categoria") idCategoria: String,
                       @PathVariable(value = "id_produto") idProduto: String,
                       @RequestBody request: ProdutoRequest): ResponseEntity<Void> {
        val produto = ProdutoDTO(idProduto, request.nome, request.descricao, request.valor, request.imagem)
        cardapioService.alterarProduto(idCategoria, idProduto, produto)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping(value = ["/categoria/{id_categoria}/produto/{id_produto}"])
    //@PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun removerProduto(@PathVariable(value = "id_produto") idProduto: String,
                       @PathVariable(value = "id_categoria") idCategoria: String): ResponseEntity<Void> {
        cardapioService.removerProduto(idProduto, idCategoria)
        return ResponseEntity.ok().build()
    }

    private fun map(cardapioDTO: CardapioDTO): CardapioResponse {
        var produtosResponse = this.mapCategorias(cardapioDTO.categorias!!)
        return CardapioResponse(cardapioDTO.id, cardapioDTO.titulo, produtosResponse)
    }

    private fun mapCategorias(categorias: List<CategoriaDTO>): List<CategoriaResponse> {
        return categorias.map { c ->
            val produtos = map(c.produtos!!)
            CategoriaResponse(c.id, c.titulo, produtos)
        }.toList()
    }

    private fun map(produtosDTO: List<ProdutoDTO>): List<ProdutoResponse> {
        return produtosDTO.map { p -> ProdutoResponse(p.id, p.nome, p.descricao, p.valor) }.toList()
    }
}