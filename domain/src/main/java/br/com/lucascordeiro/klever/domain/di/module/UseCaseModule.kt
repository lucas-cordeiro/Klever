package br.com.lucascordeiro.klever.domain.di.module

import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCase
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single { GetBankAccountUseCaseImpl(get(), get()) as GetBankAccountUseCase}
}