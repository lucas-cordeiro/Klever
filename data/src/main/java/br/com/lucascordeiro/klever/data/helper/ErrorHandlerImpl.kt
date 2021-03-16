package br.com.lucascordeiro.klever.data.helper

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity
import io.grpc.Status
import io.grpc.StatusException
import timber.log.Timber
import java.net.UnknownHostException

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        Timber.tag("ErrorHandler").d("Error: ${throwable::class.java}")
        val errorEntity = when (throwable) {
            is StatusException -> {
                Timber.tag("ErrorHandler").d("StatusException: ${throwable.status}")
                when(throwable.status.code){
                    Status.UNAVAILABLE.code -> ErrorEntity.ApiError.Network
                    Status.NOT_FOUND.code -> ErrorEntity.ApiError.NotFound
                    else -> ErrorEntity.Unknown
                }
            }
            is UnknownHostException -> ErrorEntity.ApiError.Network
            else -> ErrorEntity.Unknown
        }
        return errorEntity
    }
}