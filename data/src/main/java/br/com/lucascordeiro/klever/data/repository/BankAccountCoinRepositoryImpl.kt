package br.com.lucascordeiro.klever.data.repository

import br.com.lucascordeiro.klever.GetBankAccountCoinsRequest
import br.com.lucascordeiro.klever.data.mapper.bankAccount.coins.BankAccountCoinMapper
import br.com.lucascordeiro.klever.data.network.service.KleverService
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin
import br.com.lucascordeiro.klever.domain.repository.BankAccountCoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BankAccountCoinRepositoryImpl(
    private val kleverService: KleverService,
    private val bankAccountCoinMapper: BankAccountCoinMapper,
) : BankAccountCoinRepository {
    override suspend fun getBankAccountCoins(bankAccountId: String): Flow<List<BankAccountCoin>> {
        val request = GetBankAccountCoinsRequest.newBuilder().setBankAccountId(bankAccountId).build()
        return kleverService.getService().getBankAccountCoins(request).map { list ->
            list.dataList.map {
                bankAccountCoinMapper.provideFromNetworkToModel().map(it)!!
            }
        }
    }
}