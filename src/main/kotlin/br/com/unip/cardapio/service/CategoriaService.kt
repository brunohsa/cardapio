package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.AlterarCategoriaDTO
import br.com.unip.cardapio.dto.InserirCategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.ECodigoErro.*
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.exception.ParametroInvalidoException
import br.com.unip.cardapio.repository.ICategoriaRepository
import br.com.unip.cardapio.repository.entity.Categoria
import br.com.unip.cardapio.repository.entity.Subcategoria
import org.springframework.stereotype.Service

@Service
class CategoriaService(val categoriaRepository: ICategoriaRepository,
                       val subcategoriaService: ISubcategoriaService,
                       val produtoService: IProdutoService) : ICategoriaService {

    override fun adicionar(dto: InserirCategoriaDTO, cardapioId: String): Categoria {
        if (dto.titulo.isNullOrEmpty()) {
            throw ParametroInvalidoException(TITULO_CATEGORIA_OBRIGATORIO)
        }
        var subcategoria: Subcategoria? = null
        if (!dto.subcategoriaId.isNullOrEmpty()) {
            subcategoria = subcategoriaService.buscar(dto.subcategoriaId!!)
        }
        val categoria = Categoria(dto.titulo, subcategoria, cardapioId)
        return categoriaRepository.save(categoria)
    }

    override fun alterar(dto: AlterarCategoriaDTO, cardapioId: String) {
        val categoria = buscar(dto.categoriaId, cardapioId)
        if (!dto.titulo.isNullOrEmpty()) {
            categoria.titulo = dto.titulo
        }
        if (!dto.subcategoriaId.isNullOrEmpty()) {
            val subcategoria = subcategoriaService.buscar(dto.subcategoriaId!!)
            categoria.subcategoria = subcategoria
            categoria.produtos.forEach { p -> produtoService.atualizarSubcategoria(p.id!!, subcategoria) }
        }
        categoriaRepository.save(categoria)
    }

    override fun remover(categoriaId: String, cardapioId: String) {
        val categoria = buscar(categoriaId, cardapioId)
        categoria.produtos.forEach { p -> produtoService.remover(p.id!!, categoriaId) }

        categoriaRepository.delete(categoria)
    }

    override fun adicionarProduto(categoriaId: String, cardapioId: String, produtoDTO: ProdutoDTO): Categoria {
        val categoria = buscar(categoriaId, cardapioId)
        val subcategoriaId = categoria.subcategoria?.id
        val produto = produtoService.cadastrar(produtoDTO, categoriaId, subcategoriaId, cardapioId)
        categoria.adicionarProduto(produto)

        return categoriaRepository.save(categoria)
    }

    override fun removerProduto(categoriaId: String, cardapioId: String, produtoId: String) {
        val categoria = buscar(categoriaId, cardapioId)
        val produto = categoria.produtos.find { p -> p.id == produtoId }
                ?: throw NaoEncontradoException(PRODUTO_NAO_ENCONTRADO)
        categoria.removerProduto(produto)

        produtoService.remover(produtoId, categoriaId)
        categoriaRepository.save(categoria)
    }

    override fun buscar(id: String, cardapioId: String): Categoria {
        return categoriaRepository.findByIdAndCardapioId(id, cardapioId)
                .orElseThrow { throw NaoEncontradoException(CATEGORIA_NAO_ENCONTRADA) }
    }
}