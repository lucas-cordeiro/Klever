package br.com.lucascordeiro.klever.domain.error.errorentity

sealed class ErrorEntity(var code: Int) {

    sealed class BankAccount(code: Int = 100): ErrorEntity(code){
        object InvalidBalance: BankAccount(101)
    }

    sealed class ApiError(code: Int = 400) : ErrorEntity(code) {
        object Network : ApiError(401)
        object NotFound : ApiError(402)
        object Timeout : ApiError(403)
        object Unknown : ApiError(404)
    }

    object Unknown : ErrorEntity(1)
}