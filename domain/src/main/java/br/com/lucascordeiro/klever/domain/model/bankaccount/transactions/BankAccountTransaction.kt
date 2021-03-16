package br.com.lucascordeiro.klever.domain.model.bankaccount.transactions

data class BankAccountTransaction(
    var id: String? = null,
    var bankAccountId: String? = null,
    var credit: Boolean? = null,
    var amount: Double? = null,
    var balance: Double? = null,
    var balancePercent: Double? = null,
    var transferDate: Long? = null,
)
