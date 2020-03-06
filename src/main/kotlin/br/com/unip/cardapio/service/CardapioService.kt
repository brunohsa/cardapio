package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.ECodigoErro.TITULO_CARDAPIO_OBRIGATORIO
import br.com.unip.cardapio.exception.FornecedorPossuiCardapioException
import br.com.unip.cardapio.exception.ParametroInvalidoException
import br.com.unip.cardapio.repository.ICardapioRepository
import br.com.unip.cardapio.repository.entity.Cardapio
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.security.util.AutenthicationUtil
import org.springframework.stereotype.Service

@Service
class CardapioService(val cardapioRepository: ICardapioRepository,
                      val produtoService: IProdutoService) : ICardapioService {

    override fun criar(dto: CardapioDTO): String {
        val cadastroUUID = ""

        if (fornecedorPossuiCardapio(cadastroUUID)) {
            throw FornecedorPossuiCardapioException()
        }
        if (dto.titulo.isNullOrEmpty()) {
            throw ParametroInvalidoException(TITULO_CARDAPIO_OBRIGATORIO)
        }
        val cardapio = Cardapio(dto.titulo, cadastroUUID, true)
        cardapioRepository.save(cardapio)

        return cardapio.id!!
    }

    private fun fornecedorPossuiCardapio(uuid: String): Boolean {
        return cardapioRepository.findByUuidFornecedor(uuid) != null
    }

    override fun adicionarProduto(idCardapio: String, dto: ProdutoDTO): CardapioDTO {
        val cardapio = cardapioRepository.findById(idCardapio).orElseThrow { RuntimeException() }

        val produto = produtoService.cadastrar(idCardapio, dto)

        val produtos = cardapio.produtos
        cardapio.produtos = produtos.plus(produto)

        cardapioRepository.save(cardapio)
        return map(cardapio)!!
    }

    override fun alterarProduto(idCardapio: String, idProduto: String, produtoDTO: ProdutoDTO) {
        val cardapio = cardapioRepository.findById(idCardapio).orElseThrow { RuntimeException() }
        val produtoAtualizado = produtoService.alterar(idProduto, produtoDTO)

        val produtoPersistido = cardapio.produtos.find { p -> p.id == idProduto } ?: throw  RuntimeException()

        val produtoMutavel = cardapio.produtos.toMutableList()
        produtoMutavel.remove(produtoPersistido)
        produtoMutavel.add(produtoAtualizado)

        cardapio.produtos = produtoMutavel
        cardapioRepository.save(cardapio)
    }

    override fun removerProduto(idCardapio: String?, idProduto: String?) {
        val cardapio = cardapioRepository.findById(idCardapio!!).orElseThrow { RuntimeException() }

        val produtos = cardapio.produtos.toMutableList()

        val produto = produtos.find { p -> p.id == idProduto } ?: throw  RuntimeException()
        produtos.remove(produto)

        cardapio.produtos = produtos

        produtoService.remover(produto)
        cardapioRepository.save(cardapio)
    }

    override fun buscar(): CardapioDTO? {
        val cadastroUUID = ""

        val cardapio = cardapioRepository.findByUuidFornecedor(cadastroUUID)
        return map(cardapio)
    }

    private fun map(cardapio: Cardapio?): CardapioDTO? {
        if (cardapio == null) {
            return null
        }
        val produtosDTO = map(cardapio.produtos)
        return CardapioDTO(cardapio.id, cardapio.nome, produtosDTO)
    }

    private fun map(produtos: List<Produto>): List<ProdutoDTO> {
        return produtos.map { p ->
            ProdutoDTO(p.id, p.nome, p.descricao, p.valor.toString(), p.categoria.toString(), p.urlImagem)
        }.toList()
    }
}