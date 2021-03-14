package br.com.lucascordeiro.klever.data.di.module

import br.com.lucascordeiro.klever.data.mapper.bankAccount.BankAccountMapper
import br.com.lucascordeiro.klever.data.mapper.bankAccount.BankAccountMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    factory { BankAccountMapperImpl() as BankAccountMapper}
}