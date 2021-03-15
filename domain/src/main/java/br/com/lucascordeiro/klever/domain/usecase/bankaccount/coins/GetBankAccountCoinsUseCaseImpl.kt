package br.com.lucascordeiro.klever.domain.usecase.bankaccount.coins

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin
import br.com.lucascordeiro.klever.domain.repository.BankAccountCoinRepository
import br.com.lucascordeiro.klever.domain.repository.BankAccountRepository
import br.com.lucascordeiro.klever.domain.utils.bankAccountId
import br.com.lucascordeiro.klever.domain.utils.handleResult
import kotlinx.coroutines.flow.Flow

class GetBankAccountCoinsUseCaseImpl(
    private val bankAccountCoinRepository: BankAccountCoinRepository,
    private val errorHandler: ErrorHandler,
) : GetBankAccountCoinsUseCase {

    override suspend fun get(): Flow<Result<List<BankAccountCoin>>> {
        return bankAccountCoinRepository.getBankAccountCoins(bankAccountId).handleResult(errorHandler)
    }
}