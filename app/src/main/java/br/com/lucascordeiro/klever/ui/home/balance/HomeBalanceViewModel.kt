package br.com.lucascordeiro.klever.ui.home.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucascordeiro.klever.components.state.ViewState
import br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage.handleMessage
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.GetBankAccountTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeBalanceViewModel(
    private val getBankAccountTransactionsUseCase: GetBankAccountTransactionsUseCase
) : ViewModel() {
    private val _transactions: MutableStateFlow<List<BankAccountTransaction>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<BankAccountTransaction>>
        get() = _transactions

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state

    init {
        collectTransactions()
    }

    fun collectTransactions(){
        viewModelScope.launch {
            getBankAccountTransactionsUseCase.get().collect { result ->
                when(result){
                    is Result.Success -> {
                        _transactions.value = result.data.sortedByDescending { it.transferDate?:0L }
                    }
                    is Result.Error -> {
                        val error = result.error
                        val message = error.handleMessage("Falha ao recuperar os dados da sua conta")
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