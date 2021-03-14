package br.com.lucascordeiro.klever.data.di.module

import br.com.lucascordeiro.klever.data.helper.ErrorHandlerImpl
import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import org.koin.dsl.module

val errorModule = module {
    factory { ErrorHandlerImpl() as ErrorHandler}
}