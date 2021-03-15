package br.com.lucascordeiro.klever.data.mapper.bankAccount.transactions

import br.com.lucascordeiro.klever.BankAccount
import br.com.lucascordeiro.klever.BankAccountTransaction
import br.com.lucascordeiro.klever.data.mapper.Mapper

class BankAccountTransactionsMapperImpl : BankAccountTransactionsMapper {
    override fun provideFromNetworkToModel() = object : Mapper<BankAccountTransaction?, br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction?> {
        override fun map(input: BankAccountTransaction?): br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction? {
            return if(input!=null) br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction(
                id = input.id,
                credit = input.credit,
                amount = input.amount,
                transferDate = input.transferDate
            ) else null
        }
    }
}