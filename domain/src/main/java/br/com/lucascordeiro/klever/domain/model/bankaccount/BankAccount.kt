package br.com.lucascordeiro.klever.domain.model.bankaccount

data class BankAccount(
    var id: String? = null,
    var name: String? = null,
    var profilePicUrl: String? = null,
    var balance: Double? = null,
)
