package br.com.lucascordeiro.klever.domain.usecase.bankaccount

import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import kotlinx.coroutines.flow.Flow

interface GetBankAccountUseCase {
    suspend fun getBankAccount(): Flow<Result<BankAccount>>
}