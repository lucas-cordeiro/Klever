package br.com.lucascordeiro.klever.domain.model.bankaccount.coin

import br.com.lucascordeiro.klever.domain.model.coin.Coin

data class BankAccountCoin(
    var id: String? = null,
    var coinId: String? = null,
    var coin: Coin? = null,
    var amount: Double? = null,
)
