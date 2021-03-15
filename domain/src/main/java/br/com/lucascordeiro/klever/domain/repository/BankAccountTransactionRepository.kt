package br.com.lucascordeiro.klever.domain.repository

import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import kotlinx.coroutines.flow.Flow

interface BankAccountTransactionRepository {
    suspend fun getBankAccountTransactions(bankAccountId: String): Flow<List<BankAccountTransaction>>
}