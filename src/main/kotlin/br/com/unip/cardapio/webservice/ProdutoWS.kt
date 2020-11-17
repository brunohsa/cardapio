package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.dto.FiltroProdutosDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.security.Permissoes.ALTERAR_IMAGEM_PRODUTO
import br.com.unip.cardapio.security.Permissoes.BUSCAR_PRODUTO
import br.com.unip.cardapio.service.ICardapioService
import br.com.unip.cardapio.service.ILocalizacaoService
import br.com.unip.cardapio.service.IProdutoService
import br.com.unip.cardapio.webservice.model.request.AlterarImagemProdutoRequest
import br.com.unip.cardapio.webservice.model.response.ProdutoResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiParam
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/produtos"])
class ProdutoWS(val produtoService: IProdutoService,
                val cardapioService: ICardapioService,
                val localizacaoService: ILocalizacaoService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
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

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping(value = ["/{id_produto}"])
    fun buscarProduto(@PathVariable(value = "id_produto") idProduto: String?): ResponseEntity<ProdutoResponse> {
        var dto = produtoService.buscar(idProduto)
        return ResponseEntity.ok().body(dto.toResponse())
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping("/mais-vendidos")
    @PreAuthorize("hasAuthority('$BUSCAR_PRODUTO')")
    fun buscarProdutosMaisVendos(): ResponseEntity<List<ProdutoResponse>> {
        val cardapiosIds = cardapioService.buscarCardapios().map { c -> c.id }
        var dto = produtoService.buscarMaisVendidos(cardapiosIds)
        var response = dto.map { p -> p.toResponse() }
        return ResponseEntity.ok().body(response)
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping("/melhores-avaliados")
    @PreAuthorize("hasAuthority('$BUSCAR_PRODUTO')")
    fun buscarProdutosMelhoresAvaliados(): ResponseEntity<List<ProdutoResponse>> {
        val cardapiosIds = cardapioService.buscarCardapios().map { c -> c.id }
        var dto = produtoService.buscarMelhoresAvaliados(cardapiosIds)
        var response = dto.map { p -> p.toResponse() }
        return ResponseEntity.ok().body(response)
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping
    @PreAuthorize("hasAuthority('$BUSCAR_PRODUTO')")
    fun buscarPorFiltro(@ApiParam(required = false) @RequestParam(value = "nota_apartir_de") notaApartirDe: String?,
                        @ApiParam(required = false) @RequestParam(value = "nota_menor_que") notaMenorQue: String?,
                        @ApiParam(required = false) @RequestParam(value = "preco_apartir_de") precoApartirDe: String?,
                        @ApiParam(required = false) @RequestParam(value = "preco_menor_que") precoMenorQue: String?,
                        @ApiParam(required = false) @RequestParam(value = "nome") nome: String?,
                        @ApiParam(required = false) @RequestParam(value = "subcategoria_id") subcategoriaId: String?,
                        @ApiParam(required = false) @RequestParam(value = "tipo_ordenacao") tipoOrdenacao: String?,
                        @ApiParam(required = false) @RequestParam(value = "campo_ordenacao") campoOrdenacao: String?,
                        @ApiParam(required = false) @RequestParam(value = "limite") limite: Int?): ResponseEntity<List<ProdutoResponse>> {
        val filtro = FiltroProdutosDTO(notaApartirDe, notaMenorQue, precoApartirDe, precoMenorQue, nome, subcategoriaId,
                tipoOrdenacao, campoOrdenacao, limite)
        val cardapiosIds = cardapioService.buscarCardapios().map { c -> c.id }
        var dto = produtoService.buscarPorFiltro(cardapiosIds, filtro)

        return ResponseEntity.ok().body(dto.map { p -> p.toResponse() })
    }

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping("latitude/{latitude}/longitude/{longitude}")
    fun buscarPorFiltro(@ApiParam(required = false) @RequestParam(value = "nota_apartir_de") notaApartirDe: String?,
                        @ApiParam(required = false) @RequestParam(value = "nota_menor_que") notaMenorQue: String?,
                        @ApiParam(required = false) @RequestParam(value = "preco_apartir_de") precoApartirDe: String?,
                        @ApiParam(required = false) @RequestParam(value = "preco_menor_que") precoMenorQue: String?,
                        @ApiParam(required = false) @RequestParam(value = "nome") nome: String?,
                        @ApiParam(required = false) @RequestParam(value = "subcategoria_id") subcategoriaId: String?,
                        @ApiParam(required = false) @RequestParam(value = "tipo_ordenacao") tipoOrdenacao: String?,
                        @ApiParam(required = false) @RequestParam(value = "campo_ordenacao") campoOrdenacao: String?,
                        @ApiParam(required = false) @RequestParam(value = "limite") limite: Int?,
                        @PathVariable(value = "latitude") latitude: Double,
                        @PathVariable(value = "longitude") longitude: Double): ResponseEntity<List<ProdutoResponse>> {
        val filtro = FiltroProdutosDTO(notaApartirDe,
                notaMenorQue,
                precoApartirDe,
                precoMenorQue,
                nome,
                subcategoriaId,
                tipoOrdenacao,
                campoOrdenacao,
                limite)

        val fornecedores = localizacaoService.buscarFornecedoresUUIDPorLocalizacao(latitude, longitude)
        val cardapios = cardapioService.buscarCardapiosPorFornecedor(fornecedores)
        val cardapiosIds = cardapios.map { c -> c.id }
        var dto = produtoService.buscarPorFiltro(cardapiosIds, filtro)

        return ResponseEntity.ok().body(dto.map { p -> p.toResponse() })
    }

    private fun ProdutoDTO.toResponse() = ProdutoResponse(this.produtoId, this.nome, this.descricao, this.valor,
            this.estoque, this.nota, this.vendidos, this.urlImagem, this.cardapioId)
}