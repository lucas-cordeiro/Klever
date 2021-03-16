package br.com.lucascordeiro.klever.data.helper

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity
import timber.log.Timber

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        Timber.tag("ErrorHandler").d("Error: ${throwable.message}")
        val errorEntity = when (throwable) {
            else -> ErrorEntity.Unknown
        }
        return errorEntity
    }
}