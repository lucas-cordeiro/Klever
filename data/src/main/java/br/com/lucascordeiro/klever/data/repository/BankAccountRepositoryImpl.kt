package br.com.lucascordeiro.klever.data.repository

import br.com.lucascordeiro.klever.GetBankAccountRequest
import br.com.lucascordeiro.klever.data.mapper.bankAccount.BankAccountMapper
import br.com.lucascordeiro.klever.data.network.service.KleverService
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.repository.BankAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BankAccountRepositoryImpl(
    private val kleverService: KleverService,
    private val bankAccountMapper: BankAccountMapper,
) : BankAccountRepository {

    override suspend fun getBankAccount(bankAccountId: String): Flow<BankAccount> {
        val request = GetBankAccountRequest.newBuilder().setBankAccountId(bankAccountId).build()
        return kleverService.getService().getBankAccount(request).map {
            bankAccountMapper.provideFromNetworkToModel().map(it)!!
        }
    }
}