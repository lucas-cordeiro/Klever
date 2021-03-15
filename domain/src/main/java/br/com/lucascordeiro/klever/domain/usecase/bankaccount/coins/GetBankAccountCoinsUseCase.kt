package br.com.lucascordeiro.klever.domain.usecase.bankaccount.coins

import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin
import kotlinx.coroutines.flow.Flow

interface GetBankAccountCoinsUseCase {
    suspend fun get(): Flow<Result<List<BankAccountCoin>>>
}