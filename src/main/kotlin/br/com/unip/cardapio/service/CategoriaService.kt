package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.ECodigoErro.CATEGORIA_NAO_ENCONTRADA
import br.com.unip.cardapio.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.cardapio.exception.ECodigoErro.TITULO_CATEGORIA_OBRIGATORIO
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.exception.ParametroInvalidoException
import br.com.unip.cardapio.repository.ICategoriaRepository
import br.com.unip.cardapio.repository.entity.Categoria
import org.springframework.stereotype.Service

@Service
class CategoriaService(val categoriaRepository: ICategoriaRepository,
                       val produtoService: IProdutoService) : ICategoriaService {

    override fun adicionar(dto: CategoriaDTO, cardapioId: String): Categoria {
        if (dto.titulo.isNullOrEmpty()) {
            throw ParametroInvalidoException(TITULO_CATEGORIA_OBRIGATORIO)
        }
        val categoria = Categoria(dto.titulo, cardapioId)
        return categoriaRepository.save(categoria)
    }

    override fun remover(categoriaId: String, cardapioId: String) {
        val categoria = buscar(categoriaId, cardapioId)
        categoria.produtos.forEach { p -> produtoService.remover(p.id!!, categoriaId) }

        categoriaRepository.delete(categoria)
    }

    override fun adicionarProduto(categoriaId: String, cardapioId: String, produtoDTO: ProdutoDTO): Categoria {
        val categoria = buscar(categoriaId, cardapioId)
        val produto = produtoService.cadastrar(produtoDTO, cardapioId)
        categoria.adicionarProduto(produto)

        return categoriaRepository.save(categoria)
    }

    override fun alterarProduto(idCategoria: String, idProduto: String, dto: ProdutoDTO) {
        produtoService.alterar(idProduto, idCategoria, dto)
    }

    override fun removerProduto(categoriaId: String, cardapioId: String, produtoId: String) {
        val categoria = buscar(categoriaId, cardapioId)
        val produto = categoria.produtos.find { p -> p.id == produtoId }
                ?: throw NaoEncontradoException(PRODUTO_NAO_ENCONTRADO)
        categoria.removerProduto(produto)

        produtoService.remover(produtoId, categoriaId)
        categoriaRepository.save(categoria)
    }

    private fun buscar(id: String, cardapioId: String): Categoria {
        return categoriaRepository.findByIdAndCardapioId(id, cardapioId)
                .orElseThrow { throw NaoEncontradoException(CATEGORIA_NAO_ENCONTRADA) }
    }
}