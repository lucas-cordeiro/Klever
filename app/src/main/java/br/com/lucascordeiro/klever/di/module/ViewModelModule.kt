package br.com.lucascordeiro.klever.di.module

import br.com.lucascordeiro.klever.ui.home.HomeViewModel
import br.com.lucascordeiro.klever.ui.home.actions.HomeActionsViewModel
import br.com.lucascordeiro.klever.ui.home.balance.HomeBalanceViewModel
import br.com.lucascordeiro.klever.ui.home.coins.HomeCoinsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { HomeBalanceViewModel(get()) }
    viewModel { HomeCoinsViewModel(get()) }
    viewModel { HomeActionsViewModel(get()) }
}