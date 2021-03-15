package br.com.lucascordeiro.klever.data.di.module

import br.com.lucascordeiro.klever.data.mapper.bankAccount.BankAccountMapper
import br.com.lucascordeiro.klever.data.mapper.bankAccount.BankAccountMapperImpl
import br.com.lucascordeiro.klever.data.mapper.bankAccount.coins.BankAccountCoinMapper
import br.com.lucascordeiro.klever.data.mapper.bankAccount.coins.BankAccountCoinMapperImpl
import br.com.lucascordeiro.klever.data.mapper.bankAccount.transactions.BankAccountTransactionsMapper
import br.com.lucascordeiro.klever.data.mapper.bankAccount.transactions.BankAccountTransactionsMapperImpl
import br.com.lucascordeiro.klever.data.mapper.coin.CoinMapper
import br.com.lucascordeiro.klever.data.mapper.coin.CoinMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    factory { BankAccountMapperImpl() as BankAccountMapper}
    factory { BankAccountTransactionsMapperImpl() as BankAccountTransactionsMapper}
    factory { BankAccountCoinMapperImpl(get()) as BankAccountCoinMapper}
    factory { CoinMapperImpl() as CoinMapper}
}