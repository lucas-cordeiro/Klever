package br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions

import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction

interface AddBankAccountTransactionUseCase {
    suspend fun add(bankAccountTransaction: BankAccountTransaction): Result<Unit>
}