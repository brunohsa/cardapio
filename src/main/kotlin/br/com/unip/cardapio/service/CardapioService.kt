package br.com.unip.cardapio.service

import br.com.unip.autenticacaolib.util.AuthenticationUtil
import br.com.unip.cardapio.dto.*
import br.com.unip.cardapio.exception.ECodigoErro.CARDAPIO_NAO_ENCONTRADO
import br.com.unip.cardapio.exception.ECodigoErro.TITULO_CARDAPIO_OBRIGATORIO
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.exception.ParametroInvalidoException
import br.com.unip.cardapio.exception.PossuiItensDoCardapioEmCarrinhosException
import br.com.unip.cardapio.exception.UsuarioNaoPossuiCadastroException
import br.com.unip.cardapio.repository.ICardapioRepository
import br.com.unip.cardapio.repository.entity.Cardapio
import br.com.unip.cardapio.repository.entity.Categoria
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.repository.entity.Subcategoria
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CardapioService(val cardapioRepository: ICardapioRepository,
                      val categoriaService: ICategoriaService,
                      val produtoService: IProdutoService,
                      val carrinho: ICarrinhoService,
                      @Value("\${download.imagens.url}") val urlDownload: String) : ICardapioService {

    override fun criar(dto: CardapioDTO): String {
        val cadastroUUID = getCadastroUUID()
        if (dto.titulo.isNullOrEmpty()) {
            throw ParametroInvalidoException(TITULO_CARDAPIO_OBRIGATORIO)
        }
        if (dto.ativo) {
            this.alterarOutroCardapioParaInativo()
        }
        val cardapio = Cardapio(dto.titulo, cadastroUUID, dto.ativo)
        cardapioRepository.save(cardapio)

        return cardapio.id!!
    }

    override fun remover(idCardapio: String) {
        val cardapio = this.buscarPorId(idCardapio)
        if (possuiCarrinhosComProdutosDoCardapio(cardapio.uuidFornecedor!!)) {
            throw PossuiItensDoCardapioEmCarrinhosException()
        }
        cardapio.categorias.forEach { c -> removerCategoria(c.id!!, cardapio.id!!) }
        cardapioRepository.delete(cardapio)
    }

    private fun possuiCarrinhosComProdutosDoCardapio(fornecedorUUID: String): Boolean {
        val carrinhos = carrinho.buscarCarrinhosPorFornecedor(fornecedorUUID)
        return carrinhos.isNotEmpty()
    }

    override fun alterar(idCardapio: String, cardapioDTO: CardapioDTO) {
        val cardapio = buscarPorId(idCardapio)
        if (!cardapioDTO.titulo.isNullOrEmpty()) {
            cardapio.titulo = cardapioDTO.titulo
        }
        if (cardapioDTO.ativo) {
            this.alterarOutroCardapioParaInativo()
            cardapio.ativo = cardapioDTO.ativo
        }
        cardapioRepository.save(cardapio)
    }

    private fun alterarOutroCardapioParaInativo() {
        val cardapioAtivo = cardapioRepository.findByAtivo()
        cardapioAtivo.ifPresent { cardapio ->
            cardapio.ativo = false
            cardapioRepository.save(cardapio)
        }
    }

    override fun adicionarCategoria(dto: InserirCategoriaDTO, idCardapio: String): CardapioDTO {
        val cardapio = this.buscarPorId(idCardapio)
        val categoria = categoriaService.adicionar(dto, cardapio.id!!)
        cardapio.adicionarCategoria(categoria)

        return cardapioRepository.save(cardapio).toDTO()
    }

    override fun removerCategoria(idCategoria: String, idCardapio: String) {
        val cardapio = this.buscarPorId(idCardapio)
        val categoria = cardapio.categorias.find { c -> c.id == idCategoria }
                ?: throw NaoEncontradoException(CARDAPIO_NAO_ENCONTRADO)
        cardapio.removerCategoria(categoria)

        categoriaService.remover(idCategoria, cardapio.id!!)
        cardapioRepository.save(cardapio)
    }

    override fun adicionarProduto(idCategoria: String, produtoDTO: ProdutoDTO, idCardapio: String) {
        val cardapio = this.buscarPorId(idCardapio)
        categoriaService.adicionarProduto(idCategoria, cardapio.id!!, produtoDTO)
    }

    override fun alterarProduto(idCategoria: String, produtoDTO: ProdutoDTO) {
        produtoService.alterar(idCategoria, produtoDTO.produtoId!!, produtoDTO)
    }

    override fun removerProduto(idProduto: String, idCategoria: String, idCardapio: String) {
        val cardapio = this.buscarPorId(idCardapio)
        categoriaService.removerProduto(idCategoria, cardapio.id!!, idProduto)
    }

    override fun buscar(id: String): CardapioDTO {
        return this.buscarPorId(id).toDTO()
    }

    override fun buscarCardapios(): List<CardapioDTO> {
        return this.buscarTodosCardapios().toDTO()
    }

    private fun buscarTodosCardapios(): List<Cardapio> {
        return cardapioRepository.findByUuidFornecedor(getCadastroUUID())
    }

    private fun buscarPorId(id: String): Cardapio {
        return cardapioRepository.findById(id).orElseThrow { throw NaoEncontradoException(CARDAPIO_NAO_ENCONTRADO) }
    }

    private fun getCadastroUUID(): String {
        return AuthenticationUtil.getCadastroUUID() ?: throw UsuarioNaoPossuiCadastroException()
    }

    private fun List<Cardapio>.toDTO() = this.map { c -> c.toDTO() }

    private fun Cardapio.toDTO() = CardapioDTO(this.id, this.titulo, this.ativo, mapCategorias(this.categorias))

    private fun mapCategorias(categorias: List<Categoria>): List<CategoriaDTO> {
        return categorias.map { c ->
            CategoriaDTO(c.id, c.titulo, this.mapSubcategoria(c.subcategoria), this.mapProdutos(c.produtos))
        }.toList()
    }

    private fun mapSubcategoria(subcategoria: Subcategoria?): SubcategoriaDTO? {
        if (subcategoria == null) {
            return null
        }
        return SubcategoriaDTO(subcategoria.id, subcategoria.nome, subcategoria.descricao)
    }

    private fun mapProdutos(produtos: List<Produto>): List<ProdutoDTO> {
        return produtos.map { p ->
            ProdutoDTO(p.id, p.nome, p.descricao, p.valor.toString(), p.estoque, montarURLDownloadImagem(p), p.nota)
        }.toList()
    }

    private fun montarURLDownloadImagem(produto: Produto): String? {
        if (produto.urlImagem.isNullOrEmpty()) {
            return ""
        }
        return urlDownload.format(produto.id)
    }
}