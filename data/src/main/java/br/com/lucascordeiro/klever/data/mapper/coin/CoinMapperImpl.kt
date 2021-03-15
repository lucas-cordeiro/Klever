package br.com.lucascordeiro.klever.data.mapper.coin

import br.com.lucascordeiro.klever.Coin
import br.com.lucascordeiro.klever.data.mapper.Mapper

class CoinMapperImpl : CoinMapper {
    override fun provideFromNetworkToModel() = object : Mapper<Coin?, br.com.lucascordeiro.klever.domain.model.coin.Coin?> {
        override fun map(input: Coin?): br.com.lucascordeiro.klever.domain.model.coin.Coin? {
            return if(input!=null) br.com.lucascordeiro.klever.domain.model.coin.Coin(
                id = input.id,
                name = input.name,
                shortName = input.shortName,
                price = input.price,
                percent = input.percent,
                iconUrl = input.iconUrl
            ) else null
        }
    }
}