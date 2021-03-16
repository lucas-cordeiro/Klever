package br.com.lucascordeiro.klever.domain.model.bankaccount.transactions

sealed class TransactionRange {
    object Day: TransactionRange()
    object Week: TransactionRange()
    object Month: TransactionRange()
    object Year: TransactionRange()
}
