package br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions

import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import kotlinx.coroutines.flow.Flow

interface GetBankAccountTransactionsUseCase {
    suspend fun get(): Flow<Result<List<BankAccountTransaction>>>
}