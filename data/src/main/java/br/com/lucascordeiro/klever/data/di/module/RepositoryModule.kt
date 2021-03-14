package br.com.lucascordeiro.klever.data.di.module

import br.com.lucascordeiro.klever.data.repository.BankAccountRepositoryImpl
import br.com.lucascordeiro.klever.domain.repository.BankAccountRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { BankAccountRepositoryImpl(get(), get()) as BankAccountRepository}
}