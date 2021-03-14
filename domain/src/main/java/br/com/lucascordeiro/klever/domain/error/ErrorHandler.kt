package br.com.lucascordeiro.klever.domain.error

import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}