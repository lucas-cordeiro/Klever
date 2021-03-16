package br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage

import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

fun ErrorEntity.ApiError.handleApiMessage(defaultMessage: String) : String{
    return when(this){
        is ErrorEntity.ApiError.Network -> "Internet connection failed, please wait and try again"
        is ErrorEntity.ApiError.NotFound -> "Result not found, verify and try again"
        else -> defaultMessage
    }
}