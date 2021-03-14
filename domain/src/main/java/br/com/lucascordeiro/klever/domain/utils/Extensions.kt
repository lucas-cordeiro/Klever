package br.com.lucascordeiro.klever.domain.utils

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import br.com.lucascordeiro.klever.domain.helper.Result

fun <T> Flow<T>.handleResult(errorHandler: ErrorHandler) = this.map {
    val data: Result<T> = Result.Success(it)
    data
}
    .catch {
        it.printStackTrace()
        emit(Result.Error(errorHandler.getError(it)))
    }