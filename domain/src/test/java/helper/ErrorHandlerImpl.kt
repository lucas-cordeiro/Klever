package app.bluepay.domain.helper

import compre_e_instale.android.domain.error.ErrorEntity
import compre_e_instale.android.domain.error.ErrorHandler
import compre_e_instale.android.domain.exception.NotFoundException

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable){
            is NotFoundException -> ErrorEntity.ApiError.NotFound
            else -> ErrorEntity.Unknown
        }
    }
}