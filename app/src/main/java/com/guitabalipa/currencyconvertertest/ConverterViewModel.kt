package com.guitabalipa.currencyconvertertest

import com.guitabalipa.currencyconvertertest.base.BaseViewModel
import com.guitabalipa.currencyconvertertest.interactor.GetRatesInteractor
import com.guitabalipa.currencyconvertertest.model.domain.Rate
import com.guitabalipa.currencyconvertertest.model.domain.calculateRate
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ConverterViewModel(
    private val getRatesInteractor: GetRatesInteractor
): BaseViewModel<ConverterState, ConverterCommand>() {

    init {
        newState(ConverterState())
    }

    fun getRates(baseCountry: String, input: Double) {
        newState(currentState().copy(loading = true, input = input))

        disposables.clear()

        disposables.add(
            getRatesInteractor.invoke(baseCountry, input)
                .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
                .compose(applyFlowableAsync())
                .subscribe(::success, ::error)
        )
    }

    private fun success(rates: List<Rate>) {
        newState(currentState().copy(loading = false, rates = rates.map {
            it.copy(currency = it.value.calculateRate(currentState().input))
        }))
    }

    private fun error(t: Throwable) {
        Timber.e(t)
        newState(currentState().copy(loading = false))
    }

    fun setInput(input: Double) {
        newState(currentState().copy(input = input))
    }
}

data class ConverterState(
    val loading: Boolean = false,
    val rates: List<Rate>? = null,
    val input: Double = 1.0
) {
    fun showContent() = !loading && !rates.isNullOrEmpty()
}

sealed class ConverterCommand
