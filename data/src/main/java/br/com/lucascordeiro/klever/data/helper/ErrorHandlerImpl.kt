package br.com.lucascordeiro.klever.data.helper

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        val errorEntity = when (throwable) {
            else -> ErrorEntity.Unknown
        }
        return errorEntity
    }
}