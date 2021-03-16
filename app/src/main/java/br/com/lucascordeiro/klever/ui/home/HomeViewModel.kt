package br.com.lucascordeiro.klever.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucascordeiro.klever.components.state.ViewState
import br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage.handleMessage
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBankAccountUseCase: GetBankAccountUseCase
) : ViewModel() {

    private val _bankAccount: MutableStateFlow<BankAccount> = MutableStateFlow(BankAccount())
    val bankAccount: StateFlow<BankAccount>
        get() = _bankAccount

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state

    init {
        collectBankAccount()
    }

    fun resetState(){
        _state.value = ViewState.Idle
    }

    fun collectBankAccount(){
        viewModelScope.launch {
            getBankAccountUseCase.getBankAccount().collect { result ->
                when(result){
                    is Result.Success -> {
                        _bankAccount.value = result.data
                    }
                    is Result.Error -> {
                        val error = result.error
                        val message = error.handleMessage("Failed to recover your account data")
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