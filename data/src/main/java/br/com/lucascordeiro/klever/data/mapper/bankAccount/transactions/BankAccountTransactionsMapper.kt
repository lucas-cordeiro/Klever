package br.com.lucascordeiro.klever.data.mapper.bankAccount.transactions

import br.com.lucascordeiro.klever.BankAccount
import br.com.lucascordeiro.klever.data.mapper.Mapper
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction

interface BankAccountTransactionsMapper {
    fun provideFromNetworkToModel(): Mapper<br.com.lucascordeiro.klever.BankAccountTransaction?, BankAccountTransaction?>
}