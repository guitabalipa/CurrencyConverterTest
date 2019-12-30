package com.guitabalipa.currencyconvertertest.interactor

import com.guitabalipa.currencyconvertertest.api.RateApi
import com.guitabalipa.currencyconvertertest.model.domain.Currency
import com.guitabalipa.currencyconvertertest.model.domain.Rate
import com.guitabalipa.currencyconvertertest.model.domain.calculateRate

class GetRatesInteractor(private val api: RateApi) {

    operator fun invoke(base: String, currentInputValue: Double) = api.getRates(base).map {
        val newRates = mutableListOf<Rate>()
        newRates.add(createRateObject(it.base, 1.0, currentInputValue, true))
        it.rates.map { item ->
            newRates.add(createRateObject(item.key, item.value, currentInputValue, false))
        }
        newRates
    }

    private fun createRateObject(name: String, value: Double, inputValue: Double, hasFocus: Boolean): Rate {
        val currency = Currency.valueOf(name)
        return Rate(
            flag = currency.flag,
            name = name,
            desc = currency.desc,
            value = value,
            currency = value.calculateRate(inputValue),
            hasFocus = hasFocus
        )
    }
}
