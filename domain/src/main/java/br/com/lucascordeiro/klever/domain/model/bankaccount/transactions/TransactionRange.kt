package br.com.lucascordeiro.klever.domain.model.bankaccount.transactions

sealed class TransactionRange(val label: String) {
    object Day: TransactionRange("Today")
    object Week: TransactionRange("Week")
    object Month: TransactionRange("Month")
    object Year: TransactionRange("Year")
}
