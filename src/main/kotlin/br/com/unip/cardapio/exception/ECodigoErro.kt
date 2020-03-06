package br.com.unip.cardapio.exception

enum class ECodigoErro {

    ERRO_INESPERADO("000"),
    TOKEN_EXPIRADO("001"),
    TOKEN_INVALIDO("002"),
    CONEXAO_RECUSADA("003"),
    ACESSO_NEGADO("004"),
    CAMPO_OBRIGATORIO("005"),
    PARAMETRO_INVALIDO("006"),
    CAMPO_DEVE_SER_NUMERICO("007"),
    TITULO_CARDAPIO_OBRIGATORIO("008"),
    FORMATO_DATA_INVALIDO("009"),
    PRODUTO_NAO_ENCONTRADO("010"),
    ID_PRODUTO_OBRIGATORIO("011"),

    POSSUI_CARDAPIO_CADASTRADO("030");

    val codigo: String

    constructor(codigo: String) {
        this.codigo = codigo
    }
}