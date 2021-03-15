package br.com.lucascordeiro.klever.data.mapper.bankAccount.coins

import br.com.lucascordeiro.klever.BankAccount
import br.com.lucascordeiro.klever.data.mapper.Mapper
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin

interface BankAccountCoinMapper {
    fun provideFromNetworkToModel(): Mapper<br.com.lucascordeiro.klever.BankAccountCoin?, br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin?>
}