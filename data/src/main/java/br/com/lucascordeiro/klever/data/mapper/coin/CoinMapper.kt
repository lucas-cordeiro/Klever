package br.com.lucascordeiro.klever.data.mapper.coin

import br.com.lucascordeiro.klever.BankAccount
import br.com.lucascordeiro.klever.data.mapper.Mapper
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin

interface CoinMapper {
    fun provideFromNetworkToModel(): Mapper<br.com.lucascordeiro.klever.Coin?, br.com.lucascordeiro.klever.domain.model.coin.Coin?>
}