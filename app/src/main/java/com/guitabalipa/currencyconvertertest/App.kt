package com.guitabalipa.currencyconvertertest

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.guitabalipa.currencyconvertertest.di.AppInjector
import com.guitabalipa.currencyconvertertest.di.AutoInjector
import com.guitabalipa.currencyconvertertest.di.KodeinViewModelFactory
import com.guitabalipa.currencyconvertertest.di.module.appModule
import com.guitabalipa.currencyconvertertest.di.module.networkModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import timber.log.Timber

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@App))

        import(networkModule)
        import(appModule)

        bind<ViewModelProvider.Factory>() with singleton { KodeinViewModelFactory(kodein) }
    }

    override fun onCreate() {
        super.onCreate()

        AutoInjector.init(this)
        AppInjector.init(this)

        Timber.plant(Timber.DebugTree())
    }
}
