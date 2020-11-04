package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.FiltroProdutosDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.repository.entity.Subcategoria
import org.springframework.core.io.InputStreamResource

interface IProdutoService {

    fun cadastrar(dto: ProdutoDTO, categoriaId: String, subcategoriaId: String?, cardapioId: String): Produto

    fun remover(id: String, categoriaId: String)

    fun alterar(id: String, categoriaId: String, produtoDTO: ProdutoDTO): Produto

    fun alterarImagem(produtoId: String?, imagemBase64: String)

    fun downloadImagem(produtoId: String?): InputStreamResource

    fun buscar(id: String?): ProdutoDTO

    fun buscarMelhoresAvaliados(cardapiosIds: List<String?>): List<ProdutoDTO>

    fun atualizarSubcategoria(id: String, subcategoria: Subcategoria)

    fun buscarPorFiltro(cardapiosIds: List<String?>, filtro: FiltroProdutosDTO): List<ProdutoDTO>
}