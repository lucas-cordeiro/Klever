package br.com.lucascordeiro.klever.data.mapper.bankAccount

import br.com.lucascordeiro.klever.BankAccount
import br.com.lucascordeiro.klever.data.mapper.Mapper

class BankAccountMapperImpl : BankAccountMapper {

    override fun provideFromNetworkToModel() = object : Mapper<BankAccount?, br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount?> {
        override fun map(input: BankAccount?): br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount? {
            return if(input!=null) br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount(
                id = input.id,
                name = input.name,
                profilePicUrl = input.profilePicUrl,
                balance = input.balance
            ) else null
        }
    }
}