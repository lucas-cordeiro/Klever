package br.com.lucascordeiro.klever.domain.repository

import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import kotlinx.coroutines.flow.Flow

interface BankAccountRepository {
    suspend fun getBankAccount(bankAccountId: String): Flow<BankAccount>
}