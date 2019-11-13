package br.com.unip.cardapio.service

import br.com.unip.cardapio.domain.CardapioDomain
import br.com.unip.cardapio.dto.CardapioDTO
import br.com.unip.cardapio.dto.ProdutoDTO
import br.com.unip.cardapio.exception.FornecedorPossuiCardapioException
import br.com.unip.cardapio.repository.ICardapioRepository
import br.com.unip.cardapio.repository.entity.Cardapio
import br.com.unip.cardapio.repository.entity.Produto
import br.com.unip.cardapio.security.util.AutenthicationUtil
import org.springframework.stereotype.Service

@Service
class CardapioService(val cardapioRepository: ICardapioRepository,
                      val produtoService: IProdutoService,
                      val autenticacaoService: IAutenticacaoService) : ICardapioService {

    override fun criar(dto: CardapioDTO): String {
        val emailUsuario = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(emailUsuario)

        if (fornecedorPossuiCardapio(cadastro.uuid)) {
            throw FornecedorPossuiCardapioException()
        }
        val cardapioDomain = CardapioDomain(dto.nome)
        val cardapio = Cardapio(nome = cardapioDomain.nome.get(),
                uuidFornecedor = cadastro.uuid,
                produtos = emptyList(),
                ativo = true
        )
        cardapioRepository.save(cardapio)
        return cardapio.id!!
    }

    private fun fornecedorPossuiCardapio(uuid: String): Boolean {
        val cardapio = cardapioRepository.findByUuidFornecedor(uuid)
        return cardapio != null
    }

    override fun adicionarProduto(idCardapio: String?, dto: ProdutoDTO): CardapioDTO {
        val cardapio = cardapioRepository.findById(idCardapio!!).orElseThrow { RuntimeException() }

        val produto = produtoService.cadastrar(idCardapio, dto)

        val produtos = cardapio.produtos
        cardapio.produtos = produtos.plus(produto)

        cardapioRepository.save(cardapio)

        return map(cardapio)
    }

    override fun buscar(): CardapioDTO {
        val emailUsuario = AutenthicationUtil.getUsuarioLogado()
        val cadastro = autenticacaoService.buscarCadastroPorEmail(emailUsuario)

        val cardapio = cardapioRepository.findByUuidFornecedor(cadastro.uuid)
        return map(cardapio!!)
    }

    private fun map(cardapio: Cardapio): CardapioDTO {
        val produtosDTO = map(cardapio.produtos)
        return CardapioDTO(cardapio.id, cardapio.nome, produtosDTO)
    }

    private fun map(produtos: List<Produto>): List<ProdutoDTO> {
        return produtos.map { p ->
            ProdutoDTO(p.id, p.nome, p.descricao, p.valor.toString(), p.categoria.toString(), p.urlImagem)
        }.toList()
    }
}