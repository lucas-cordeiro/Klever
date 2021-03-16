package br.com.lucascordeiro.klever.ui.home.actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucascordeiro.klever.components.state.ViewState
import br.com.lucascordeiro.klever.domain.error.errorentity.ErrorEntity
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.AddBankAccountTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeActionsViewModel(
    private val addBankAccountTransactionUseCase: AddBankAccountTransactionUseCase,
) : ViewModel(){

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val state: StateFlow<ViewState>
        get() = _state

    fun resetState(){
        _state.value = ViewState.Idle
    }

    fun addTransaction(balance: Double, credit: Boolean){
        viewModelScope.launch {
            if(!credit && balance - 100 < 0){
                _state.value = ViewState.Error(
                    message = "It is necessary to have a balance of at least $100",
                    code = ErrorEntity.BankAccount.InvalidBalance.code
                )
                return@launch
            }
            _state.value = ViewState.Loading
            addBankAccountTransactionUseCase.add(
                BankAccountTransaction(
                    amount = 100.0,
                    credit = credit,
                )
            )
            _state.value = ViewState.Success(
                message = "Transaction successfully completed!"
            )
        }
    }
}