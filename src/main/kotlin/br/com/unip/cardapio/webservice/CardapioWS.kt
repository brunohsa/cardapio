package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.dto.*
import br.com.unip.cardapio.security.Permissoes.ALTERAR_CARDAPIO
import br.com.unip.cardapio.security.Permissoes.BUSCAR_CARDAPIO
import br.com.unip.cardapio.security.Permissoes.CRIAR_CARDAPIO
import br.com.unip.cardapio.service.ICardapioService
import br.com.unip.cardapio.service.ICarrinhoService
import br.com.unip.cardapio.service.ICategoriaService
import br.com.unip.cardapio.webservice.model.request.CardapioRequest
import br.com.unip.cardapio.webservice.model.request.CategoriaRequest
import br.com.unip.cardapio.webservice.model.request.ProdutoRequest
import br.com.unip.cardapio.webservice.model.response.CardapioResponse
import br.com.unip.cardapio.webservice.model.response.CategoriaResponse
import br.com.unip.cardapio.webservice.model.response.ProdutoResponse
import br.com.unip.cardapio.webservice.model.response.SubcategoriaResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/v1/cardapios"])
class CardapioWS(val cardapioService: ICardapioService,
                 val categoriaService: ICategoriaService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PostMapping(value = ["/criar"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasAuthority('$CRIAR_CARDAPIO')")
    fun criarCardapio(@RequestBody request: CardapioRequest): ResponseEntity<List<CardapioResponse>> {
        val dto = CardapioDTO(request.nome, request.ativo)
        cardapioService.criar(dto)
        val cardapiosDTO = cardapioService.buscarCardapios()
        return ResponseEntity.ok(cardapiosDTO.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @DeleteMapping(value = ["/{id_cardapio}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasAuthority('$CRIAR_CARDAPIO')")
    fun removerCardapio(@PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<List<CardapioResponse>> {
        cardapioService.remover(idCardapio)
        val cardapios = cardapioService.buscarCardapios()
        return ResponseEntity.ok(cardapios.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping(value = ["/{id_cardapio}"])
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarCardapio(@PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<CardapioResponse> {
        val dto = cardapioService.buscar(idCardapio)
        return ResponseEntity.ok(dto.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping(value = ["/cadastro/{cadastro_uuid}"])
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarCardapioPorFornecedor(@PathVariable(value = "fornecedor_uuid") fornecedorUUID: String):
            ResponseEntity<CardapioResponse?> {
        val dto = cardapioService.buscarPorFornecedorUUID(fornecedorUUID)
        var response: CardapioResponse? = null
        if (dto != null) {
            response = dto.toResponse()
        }
        return ResponseEntity.ok().body(response)
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/alterar"])
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun alterarCardapio(@PathVariable(value = "id_cardapio") idCardapio: String,
                        @RequestBody request: CardapioRequest): ResponseEntity<Void> {
        val dto = CardapioDTO(request.nome, request.ativo)
        cardapioService.alterar(idCardapio, dto)
        return ResponseEntity.ok().build()
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarCardapios(): ResponseEntity<List<CardapioResponse>> {
        val dto = cardapioService.buscarCardapios()
        return ResponseEntity.ok(dto.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/adicionar-categoria"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun adicionarCategoria(@RequestBody request: CategoriaRequest,
                           @PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<CardapioResponse> {
        val categoria = InserirCategoriaDTO(request.titulo, request.subcategoriaId)
        val cardapio = cardapioService.adicionarCategoria(categoria, idCardapio)
        return ResponseEntity.ok(cardapio.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/cartegoria/{id_categoria}/alterar"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun alterarCategoria(@PathVariable(value = "id_cardapio") idCardapio: String,
                         @PathVariable(value = "id_categoria") idCategoria: String,
                         @RequestBody request: CategoriaRequest): ResponseEntity<Void> {
        val categoria = AlterarCategoriaDTO(idCategoria, request.titulo, request.subcategoriaId)
        categoriaService.alterar(categoria, idCardapio)
        return ResponseEntity.ok().build()
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @DeleteMapping(value = ["/{id_cardapio}/categoria/{id_categoria}"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun removerCategoria(@PathVariable(value = "id_categoria") idCategoria: String,
                         @PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<CardapioResponse> {
        cardapioService.removerCategoria(idCategoria, idCardapio)
        val cardapio = cardapioService.buscar(idCardapio)
        return ResponseEntity.ok(cardapio.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/categoria/{id_categoria}/adicionar-produto"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun adicionarProduto(@PathVariable(value = "id_categoria") idCategoria: String,
                         @PathVariable(value = "id_cardapio") idCardapio: String,
                         @RequestBody request: ProdutoRequest): ResponseEntity<CardapioResponse> {
        val produto = ProdutoDTO(request.nome, request.descricao, request.valor, request.estoque)
        cardapioService.adicionarProduto(idCategoria, produto, idCardapio)
        val cardapio = cardapioService.buscar(idCardapio)
        return ResponseEntity.ok(cardapio.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @PutMapping(value = ["/{id_cardapio}/categoria/{id_categoria}/produto/{id_produto}/alterar"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun alterarProduto(@PathVariable(value = "id_cardapio") idCardapio: String,
                       @PathVariable(value = "id_categoria") idCategoria: String,
                       @PathVariable(value = "id_produto") idProduto: String,
                       @RequestBody request: ProdutoRequest): ResponseEntity<CardapioResponse> {
        val produto = ProdutoDTO(idProduto, request.nome, request.descricao, request.valor, request.estoque)
        cardapioService.alterarProduto(idCategoria, produto)
        val cardapio = cardapioService.buscar(idCardapio)
        return ResponseEntity.ok(cardapio.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @DeleteMapping(value = ["/{id_cardapio}/categoria/{id_categoria}/produto/{id_produto}"])
    @PreAuthorize("hasAuthority('$ALTERAR_CARDAPIO')")
    fun removerProduto(@PathVariable(value = "id_produto") idProduto: String,
                       @PathVariable(value = "id_categoria") idCategoria: String,
                       @PathVariable(value = "id_cardapio") idCardapio: String): ResponseEntity<CardapioResponse> {
        cardapioService.removerProduto(idProduto, idCategoria, idCardapio)
        val cardapio = cardapioService.buscar(idCardapio)
        return ResponseEntity.ok(cardapio.toResponse())
    }

    private fun List<CardapioDTO>.toResponse() = this.map { c -> c.toResponse() }

    private fun CardapioDTO.toResponse() =
            CardapioResponse(this.id, this.titulo, mapCategorias(this.categorias!!), this.ativo)

    private fun mapCategorias(categorias: List<CategoriaDTO>): List<CategoriaResponse> {
        return categorias.map { c ->
            CategoriaResponse(c.id, c.titulo, c.subcategoria?.toResponse(), map(c.produtos!!))
        }.toList()
    }

    private fun SubcategoriaDTO.toResponse() = SubcategoriaResponse(this.id, this.descricao)

    private fun map(produtosDTO: List<ProdutoDTO>): List<ProdutoResponse> {
        return produtosDTO.map { p ->
            ProdutoResponse(p.produtoId, p.nome, p.descricao, p.valor, p.estoque, p.nota, p.vendidos, p.urlImagem)
        }.toList()
    }
}