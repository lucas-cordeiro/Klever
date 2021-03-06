package br.com.lucascordeiro.klever.domain.helper

import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val error: ErrorEntity) : Result<T>()
}