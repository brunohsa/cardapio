package br.com.unip.cardapio.webservice

import br.com.unip.cardapio.dto.SubcategoriaDTO
import br.com.unip.cardapio.security.Permissoes.BUSCAR_CARDAPIO
import br.com.unip.cardapio.service.ISubcategoriaService
import br.com.unip.cardapio.webservice.model.response.SubcategoriaResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/categorias"])
class CategoriaWS(val subcategoriaService: ISubcategoriaService) {

    @ApiImplicitParams(ApiImplicitParam(name = "token", value = "Token", required = true, paramType = "header"))
    @GetMapping(value = ["/subcategorias"])
    @PreAuthorize("hasAuthority('$BUSCAR_CARDAPIO')")
    fun buscarSubcategorias(): ResponseEntity<List<SubcategoriaResponse>> {
        val dto = subcategoriaService.buscarTodas()
        return ResponseEntity.ok(dto.toResponse())
    }

    private fun List<SubcategoriaDTO>.toResponse() =
            this.map { s -> SubcategoriaResponse(s.id,  s.descricao, s.nome) }
}