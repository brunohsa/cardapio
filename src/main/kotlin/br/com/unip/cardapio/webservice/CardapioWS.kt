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
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer
import org.springframework.http.MediaType
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

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PostMapping(value = ["/criar"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasAuthority('$CRIAR_CARDAPIO')")
    fun criarCardapio(@RequestBody request: CardapioRequest): ResponseEntity<List<CardapioResponse>> {
        val dto = CardapioDTO(request.nome, request.ativo)
        cardapioService.criar(dto)
        val cardapiosDTO = cardapioService.buscarCardapios()

        return ResponseEntity.ok(cardapiosDTO.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true,
            dataType = "string", paramType = "header"))
    @GetMapping(value = ["/{id_cardapio}"])
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarCardapio(@PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<CardapioResponse> {
        val dto = cardapioService.buscar(idCardapio)
        return ResponseEntity.ok(dto.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true,
                                        dataType = "string", paramType = "header"))
    @GetMapping
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarCardapios(): ResponseEntity<List<CardapioResponse>> {
        val dto = cardapioService.buscarCardapios()
        return ResponseEntity.ok(dto.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true,
            dataType = "string", paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/adicionar-categoria"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun adicionarCategoria(@RequestBody request: CategoriaRequest,
                           @PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<CardapioResponse> {
        val categoria = CategoriaDTO(request.titulo)
        val cardapio = cardapioService.adicionarCategoria(categoria, idCardapio)
        return ResponseEntity.ok(cardapio.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true,
            dataType = "string", paramType = "header"))
    @DeleteMapping(value = ["/{id_cardapio}/categoria/{id_categoria}"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun removerCategoria(@PathVariable(value = "id_categoria") idCategoria: String,
                         @PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<Void> {
        cardapioService.removerCategoria(idCategoria, idCardapio)
        return ResponseEntity.ok().build()
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true,
            dataType = "string", paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/categoria/{id_categoria}/adicionar-produto"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun adicionarProduto(@PathVariable(value = "id_categoria") idCategoria: String,
                         @PathVariable(value = "id_cardapio") idCardapio: String,
                         @RequestBody request: ProdutoRequest): ResponseEntity<CardapioResponse> {
        val produto = ProdutoDTO(request.nome, request.descricao, request.valor, request.imagem)
        cardapioService.adicionarProduto(idCategoria, produto, idCardapio)
        val cardapio = cardapioService.buscar(idCardapio)

        return ResponseEntity.ok(cardapio.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true,
            dataType = "string", paramType = "header"))
    @PutMapping(value = ["/categoria/{id_categoria}/produto/{id_produto}/alterar"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun alterarProduto(@PathVariable(value = "id_categoria") idCategoria: String,
                       @PathVariable(value = "id_produto") idProduto: String,
                       @RequestBody request: ProdutoRequest): ResponseEntity<Void> {
        val produto = ProdutoDTO(idProduto, request.nome, request.descricao, request.valor, request.imagem)
        cardapioService.alterarProduto(idCategoria, idProduto, produto)

        return ResponseEntity.ok().build()
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @DeleteMapping(value = ["/{id_cardapio}/categoria/{id_categoria}/produto/{id_produto}"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun removerProduto(@PathVariable(value = "id_produto") idProduto: String,
                       @PathVariable(value = "id_categoria") idCategoria: String,
                       @PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<Void> {
        cardapioService.removerProduto(idProduto, idCategoria, idCardapio)
        return ResponseEntity.ok().build()
    }

    private fun List<CardapioDTO>.toResponse() = this.map { c -> c.toResponse() }

    private fun CardapioDTO.toResponse() =
            CardapioResponse(this.id, this.titulo, mapCategorias(this.categorias!!), this.ativo)

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