package br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage

import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

fun ErrorEntity.handleMessage(defaultMessage: String) : String {

    return when(this){
        is ErrorEntity.ApiError -> {
           this.handleApiMessage(defaultMessage)
        }
        is ErrorEntity.App -> {
            this.handleValidateMessage(defaultMessage)
        }
        else -> defaultMessage
    }
}