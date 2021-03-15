package br.com.lucascordeiro.klever.data.mapper.bankAccount.coins

import br.com.lucascordeiro.klever.data.mapper.Mapper
import br.com.lucascordeiro.klever.data.mapper.coin.CoinMapper
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin

class BankAccountCoinMapperImpl(
    private val coinMapper: CoinMapper,
) : BankAccountCoinMapper {
    override fun provideFromNetworkToModel() = object : Mapper<br.com.lucascordeiro.klever.BankAccountCoin?, BankAccountCoin?> {
        override fun map(input: br.com.lucascordeiro.klever.BankAccountCoin?): BankAccountCoin? {
            return if(input!=null) BankAccountCoin(
                id = input.id,
                coinId = input.coinId,
                coin = coinMapper.provideFromNetworkToModel().map(input.coin),
                amount = input.amount,
            ) else null
        }
    }
}