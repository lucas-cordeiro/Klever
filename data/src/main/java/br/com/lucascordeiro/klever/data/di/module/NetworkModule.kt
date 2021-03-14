package br.com.lucascordeiro.klever.data.di.module

import br.com.lucascordeiro.klever.data.network.httpurl.provideHttpUrlApp
import br.com.lucascordeiro.klever.data.network.service.KleverService
import br.com.lucascordeiro.klever.data.network.service.KleverServiceImpl
import org.koin.dsl.module

val networkModule = module {

    //HttpUrl
    single { provideHttpUrlApp() }

    //KleverService
    single { KleverServiceImpl(get()) as KleverService}
}