package br.com.lucascordeiro.klever.domain.di.module

import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCase
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCaseImpl
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.coins.GetBankAccountCoinsUseCase
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.coins.GetBankAccountCoinsUseCaseImpl
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.AddBankAccountTransactionUseCase
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.AddBankAccountTransactionUseCaseImpl
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.GetBankAccountTransactionsUseCase
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.GetBankAccountTransactionsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single { GetBankAccountUseCaseImpl(get(), get()) as GetBankAccountUseCase}
    single { GetBankAccountTransactionsUseCaseImpl(get(), get()) as GetBankAccountTransactionsUseCase}
    single { GetBankAccountCoinsUseCaseImpl(get(), get()) as GetBankAccountCoinsUseCase}
    single { AddBankAccountTransactionUseCaseImpl(get(), get()) as AddBankAccountTransactionUseCase}
}