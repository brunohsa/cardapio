package br.com.unip.cardapio.service

import br.com.unip.cardapio.dto.SubcategoriaDTO
import br.com.unip.cardapio.exception.ECodigoErro.SUBCATEGORIA_NAO_ENCONTRADA
import br.com.unip.cardapio.exception.NaoEncontradoException
import br.com.unip.cardapio.repository.ISubcategoriaRepository
import br.com.unip.cardapio.repository.entity.Subcategoria
import org.springframework.stereotype.Service

@Service
class SubcategoriaService(val subcategoriaRepository: ISubcategoriaRepository) : ISubcategoriaService {

    override fun buscar(id: String): Subcategoria {
        return subcategoriaRepository.findById(id).orElseThrow { NaoEncontradoException(SUBCATEGORIA_NAO_ENCONTRADA) }
    }

    override fun buscarTodas(): List<SubcategoriaDTO> {
        val subcategorias = subcategoriaRepository.findAll()
        return subcategorias.toDTO()
    }

    private fun List<Subcategoria>.toDTO() = this.map { s -> SubcategoriaDTO(s.id, s.nome, s.descricao) }
}