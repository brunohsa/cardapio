package br.com.unip.cardapio.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.CategoriaDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.ECodigoErro.CARDAPIO_NAO_ENCONTRADO
import br.com.unip.cardapio.exception.ECodigoErro.PRODUTO_NAO_ENCONTRADO
import br.com.unip.cardapio.exception.ECodigoErro.TITULO_CARDAPIO_OBRIGATORIO
import br.com.unip.cardapio.exception.FornecedorPossuiCardapioException
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.exception.ParametroInvalidoException
import br.com.unip.cardapio.exception.UsuarioNaoPossuiCadastroException
import br.com.unip.cardapio.repository.ICardapioRepository
import br.com.unip.cardapio.repository.entity.Cardapio
import br.com.unip.cardapio.repository.entity.Categoria
import br.com.unip.cardapio.repository.entity.Produto
import org.springframework.stereotype.Service

@Service
class CardapioService(val cardapioRepository: ICardapioRepository,
                      val categoriaService: ICategoriaService) : ICardapioService {

    override fun criar(dto: CardapioDTO): String {
        val cadastroUUID = getCadastroUUID()
        if (cardapioRepository.findByUuidFornecedor(cadastroUUID).isPresent) {
            throw FornecedorPossuiCardapioException()
        }
        if (dto.titulo.isNullOrEmpty()) {
            throw ParametroInvalidoException(TITULO_CARDAPIO_OBRIGATORIO)
        }
        val cardapio = Cardapio(dto.titulo, cadastroUUID)
        cardapioRepository.save(cardapio)

        return cardapio.id!!
    }

    override fun adicionarCategoria(dto: CategoriaDTO): CardapioDTO {
        val cardapio = this.buscarCardapio()
        val categoria = categoriaService.adicionar(dto, cardapio.id!!)
        cardapio.adicionarCategoria(categoria)

        return mapCardapio(cardapioRepository.save(cardapio))
    }

    override fun removerCategoria(idCategoria: String) {
        val cardapio = this.buscarCardapio()
        val categoria = cardapio.categorias.find { c -> c.id == idCategoria }
                ?: throw NaoEncontradoException(PRODUTO_NAO_ENCONTRADO)
        cardapio.removerCategoria(categoria)

        categoriaService.remover(idCategoria, cardapio.id!!)
        cardapioRepository.save(cardapio)
    }

    override fun adicionarProduto(idCategoria: String, produtoDTO: ProdutoDTO) {
        val cardapio = this.buscarCardapio()
        categoriaService.adicionarProduto(idCategoria, cardapio.id!!, produtoDTO)
    }

    override fun alterarProduto(idCategoria: String, idProduto: String, produtoDTO: ProdutoDTO) {
        categoriaService.alterarProduto(idCategoria, idProduto, produtoDTO)
    }

    override fun removerProduto(idProduto: String, idCategoria: String) {
        val cardapio = this.buscarCardapio()
        categoriaService.removerProduto(idCategoria, cardapio.id!!, idProduto)
    }

    override fun buscar(): CardapioDTO {
        return mapCardapio(this.buscarCardapio())
    }

    private fun buscarCardapio(): Cardapio {
        return cardapioRepository.findByUuidFornecedor(getCadastroUUID())
                .orElseThrow { throw NaoEncontradoException(CARDAPIO_NAO_ENCONTRADO) }
    }

    private fun getCadastroUUID(): String {
        return AuthenticationUtil.getCadastroUUID() ?: throw UsuarioNaoPossuiCadastroException()
    }

    private fun mapCardapio(cardapio: Cardapio): CardapioDTO {
        return CardapioDTO(cardapio.id, cardapio.titulo, this.mapCategorias(cardapio.categorias))
    }

    private fun mapCategorias(categorias: List<Categoria>): List<CategoriaDTO> {
        return categorias.map { c -> CategoriaDTO(c.id, c.titulo, this.mapProdutos(c.produtos)) }.toList()
    }

    private fun mapProdutos(produtos: List<Produto>): List<ProdutoDTO> {
        return produtos.map { p -> ProdutoDTO(p.id, p.nome, p.descricao, p.valor.toString(), p.urlImagem) }.toList()
    }
}