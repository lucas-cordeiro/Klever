package br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage

import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

fun ErrorEntity.BankAccount.handleValidateMessage(defaultMessage: String) : String{
    return  when(this){
        else -> defaultMessage
    }
}