package br.com.lucascordeiro.klever.domain.usecase.bankaccount

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.repository.BankAccountRepository
import br.com.lucascordeiro.klever.domain.utils.handleResult
import kotlinx.coroutines.flow.Flow

class GetBankAccountUseCaseImpl(
    private val bankAccountRepository: BankAccountRepository,
    private val errorHandler: ErrorHandler,
) : GetBankAccountUseCase {
    override suspend fun getBankAccount(): Flow<Result<BankAccount>> {
        return bankAccountRepository.getBankAccount().handleResult(errorHandler)
    }
}