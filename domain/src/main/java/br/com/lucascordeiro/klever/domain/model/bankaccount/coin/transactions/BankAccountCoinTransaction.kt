package br.com.lucascordeiro.klever.domain.model.bankaccount.coin.transactions

data class BankAccountCoinTransaction(
    var id: String? = null,
    var bankAccountId: String? = null,
    var coinId: String? = null,
    var credit: Boolean? = null,
    var amount: Double? = null,
    var transferDate: Long? = null,
)
