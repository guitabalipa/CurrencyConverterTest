package com.guitabalipa.currencyconvertertest.di.module

import com.guitabalipa.currencyconvertertest.ConverterViewModel
import com.guitabalipa.currencyconvertertest.interactor.GetRatesInteractor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val converterModule = Kodein.Module("Converter Module") {

    bind() from provider {
        ConverterViewModel(
            GetRatesInteractor(instance())
        )
    }
}
