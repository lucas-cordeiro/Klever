package br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.repository.BankAccountTransactionRepository
import br.com.lucascordeiro.klever.domain.utils.bankAccountId
import br.com.lucascordeiro.klever.domain.utils.handleResult
import kotlinx.coroutines.flow.Flow

class GetBankAccountTransactionsUseCaseImpl(
    private val bankAccountTransactionRepository: BankAccountTransactionRepository,
    private val errorHandler: ErrorHandler,
) : GetBankAccountTransactionsUseCase {
    override suspend fun get(): Flow<Result<List<BankAccountTransaction>>> {
        return bankAccountTransactionRepository.getBankAccountTransactions(bankAccountId).handleResult(errorHandler)
    }
}