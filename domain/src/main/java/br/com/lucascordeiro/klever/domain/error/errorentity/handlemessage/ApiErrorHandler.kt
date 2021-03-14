package br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage

import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

fun ErrorEntity.ApiError.handleApiMessage(defaultMessage: String) : String{
    return when(this){
        is ErrorEntity.ApiError.Network -> "Falha com a conexão de internet, verifique e tente novamente"
        is ErrorEntity.ApiError.NotFound -> "Resultado não encontrado, tente novamente"
        else -> defaultMessage
    }
}