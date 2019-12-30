package com.guitabalipa.currencyconvertertest.di.module

import org.kodein.di.Kodein

val appModule = Kodein.Module("App Module") {
    import(converterModule)
}
