package br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.repository.BankAccountTransactionRepository

class AddBankAccountTransactionUseCaseImpl(
    private val transactionRepository: BankAccountTransactionRepository,
    private val errorHandler: ErrorHandler,
) : AddBankAccountTransactionUseCase {
    override suspend fun add(bankAccountTransaction: BankAccountTransaction): Result<Unit> {
        try {
            return Result.Success(transactionRepository.add(bankAccountTransaction))
        }catch (t: Throwable){
            return Result.Error(errorHandler.getError(t))
        }
    }
}