package br.com.lucascordeiro.klever.data.mapper.bankAccount

import br.com.lucascordeiro.klever.BankAccount
import br.com.lucascordeiro.klever.data.mapper.Mapper

interface BankAccountMapper {
    fun provideFromNetworkToModel(): Mapper<BankAccount?, br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount?>
}