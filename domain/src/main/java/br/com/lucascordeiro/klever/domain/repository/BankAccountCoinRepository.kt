package br.com.lucascordeiro.klever.domain.repository

import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import kotlinx.coroutines.flow.Flow

interface BankAccountCoinRepository {
    suspend fun getBankAccountCoins(bankAccountId: String): Flow<List<BankAccountCoin>>
}