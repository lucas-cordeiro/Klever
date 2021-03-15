package br.com.lucascordeiro.klever.data.repository

import br.com.lucascordeiro.klever.GetBankAccountRequest
import br.com.lucascordeiro.klever.GetBankAccountTransactionsRequest
import br.com.lucascordeiro.klever.data.mapper.bankAccount.transactions.BankAccountTransactionsMapper
import br.com.lucascordeiro.klever.data.network.service.KleverService
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.repository.BankAccountTransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BankAccountTransactionRepositoryImpl(
    private val kleverService: KleverService,
    private val bankAccountTransactionsMapper: BankAccountTransactionsMapper,
) : BankAccountTransactionRepository {

    override suspend fun getBankAccountTransactions(bankAccountId: String): Flow<List<BankAccountTransaction>> {
        val request = GetBankAccountTransactionsRequest.newBuilder().setBankAccountId(bankAccountId).build()
        return kleverService.getService().getBankAccountTransactions(request).map { list ->
            list.dataList.map {
                bankAccountTransactionsMapper.provideFromNetworkToModel().map(it)!!
            }
        }
    }
}