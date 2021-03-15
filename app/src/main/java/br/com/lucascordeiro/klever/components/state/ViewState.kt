package br.com.lucascordeiro.klever.components.state

sealed class ViewState{
    object Loading: ViewState()
    class Error(val message: String, val code: Int? = null): ViewState()
    class Success(val message: String): ViewState()
}
