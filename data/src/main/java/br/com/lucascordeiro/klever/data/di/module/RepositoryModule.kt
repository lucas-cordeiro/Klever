package br.com.lucascordeiro.klever.data.di.module

import br.com.lucascordeiro.klever.data.repository.BankAccountCoinRepositoryImpl
import br.com.lucascordeiro.klever.data.repository.BankAccountRepositoryImpl
import br.com.lucascordeiro.klever.data.repository.BankAccountTransactionRepositoryImpl
import br.com.lucascordeiro.klever.domain.repository.BankAccountCoinRepository
import br.com.lucascordeiro.klever.domain.repository.BankAccountRepository
import br.com.lucascordeiro.klever.domain.repository.BankAccountTransactionRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { BankAccountRepositoryImpl(get(), get()) as BankAccountRepository}
    single { BankAccountTransactionRepositoryImpl(get(), get()) as BankAccountTransactionRepository}
    single { BankAccountCoinRepositoryImpl(get(), get()) as BankAccountCoinRepository}
}