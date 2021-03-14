package br.com.lucascordeiro.klever.domain.error.errorentity

sealed class ErrorEntity(var code: Int) {

    sealed class ApiError(code: Int = 400) : ErrorEntity(code) {
        object Network : ApiError(401)
        object NotFound : ApiError(402)
        object Timeout : ApiError(403)
        object Unknown : ApiError(404)
    }

    sealed class App(code: Int = 100) : ErrorEntity(code) {

    }

    sealed class Security(code: Int = 200): ErrorEntity(code){

    }

    sealed class BankAccount(code: Int = 300): ErrorEntity(code){

    }

    sealed class UI(code: Int = 400): ErrorEntity(code){

    }
    sealed class Permissions(code: Int = 500): ErrorEntity(code){

    }


    object Unknown : ErrorEntity(1)
}