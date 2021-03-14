package br.com.lucascordeiro.klever.domain.model.coin

data class Coin(
    var id: String? = null,
    var name: String? = null,
    var shortName: String? = null,
    var price: Double? = null,
    var percent: Float? = null,
    var iconUrl: String? = null,
)
