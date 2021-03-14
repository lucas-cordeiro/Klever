package br.com.lucascordeiro.klever.app.startup

import android.content.Context
import androidx.startup.Initializer
import br.com.lucascordeiro.klever.data.di.module.errorModule
import br.com.lucascordeiro.klever.data.di.module.mapperModule
import br.com.lucascordeiro.klever.data.di.module.networkModule
import br.com.lucascordeiro.klever.data.di.module.repositoryModule
import br.com.lucascordeiro.klever.domain.di.module.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return  startKoin {
            androidContext(context)
            androidLogger(Level.INFO)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                mapperModule,
                errorModule
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}