package br.com.lucascordeiro.klever.ui.home.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucascordeiro.klever.components.state.ViewState
import br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage.handleMessage
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.transactions.BankAccountCoinTransaction
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.coins.GetBankAccountCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeCoinsViewModel(
    private val getBankAccountCoinsUseCase: GetBankAccountCoinsUseCase,
) : ViewModel() {
    private val _bankAccountCoins: MutableStateFlow<List<BankAccountCoin>> = MutableStateFlow(emptyList())
    val bankAccountCoins: StateFlow<List<BankAccountCoin>>
        get() = _bankAccountCoins

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state

    init {
        collectBankAccountCoins()
    }

    fun collectBankAccountCoins(){
        viewModelScope.launch {
            getBankAccountCoinsUseCase.get().collect { result ->
                when(result){
                    is Result.Success -> {
                        _bankAccountCoins.value = result.data
                    }
                    is Result.Error -> {
                        val error = result.error
                        val message = error.handleMessage("Falha ao recuperar os dados de suas moedas")
                        val code = error.code
                        _state.value = ViewState.Error(
                            message = message,
                            code = code
                        )
                    }
                }
            }
        }
    }
}