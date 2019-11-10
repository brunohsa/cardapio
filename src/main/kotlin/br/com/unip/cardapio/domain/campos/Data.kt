package br.com.unip.cardapio.domain.campos

import br.com.unip.cardapio.exception.ECodigoErro
import br.com.unip.cardapio.exception.ParametroInvalidoException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class Data : ICampo<LocalDate?> {

    private val DATE_FORMAT: String = "dd/MM/yyyy"

    val data: LocalDate?

    constructor(data: String?) {
        try {
            if (data.isNullOrEmpty()) {
                this.data = null
            } else {
                val format: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                this.data = LocalDate.parse(data, format)
            }
        } catch (ex: DateTimeParseException) {
            throw ParametroInvalidoException("Formato da data inválido.", ECodigoErro.CAD012)
        }
    }

    override fun get(): LocalDate? {
        return data
    }
}