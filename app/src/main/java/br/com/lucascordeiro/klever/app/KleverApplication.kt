package br.com.lucascordeiro.klever.app

import android.app.Application
import br.com.lucascordeiro.klever.BuildConfig
import timber.log.Timber

class KleverApplication:  Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}