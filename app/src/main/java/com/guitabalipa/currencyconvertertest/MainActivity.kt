package com.guitabalipa.currencyconvertertest

import android.os.Bundle
import androidx.core.view.isVisible
import com.example.testcurrencies.R
import com.guitabalipa.currencyconvertertest.base.BaseViewModelActivity
import com.guitabalipa.currencyconvertertest.model.domain.Rate
import com.guitabalipa.currencyconvertertest.model.domain.calculateRate
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance

class MainActivity : BaseViewModelActivity() {

    private val viewModel: ConverterViewModel by instance()

    lateinit var adapter: RateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RateAdapter(
            this,
            { name, input ->
                viewModel.getRates(name, input)
            },
            {
                viewModel.setInput(it)
                updateList(it)
            })

        recyclerView.adapter = adapter

        viewModel.bind(this::render)

        viewModel.getRates("EUR", 1.0)
    }

    private fun render(state: ConverterState) {
        state::loading partTo ::renderLoading
        state::rates partTo ::renderRates

        part("show_content", state.showContent(), ::renderContent)
    }

    private fun renderRates(rates: List<Rate>?) {
        adapter.submitList(rates)
    }

    private fun renderLoading(isLoading: Boolean) {
        loadingView.isVisible = isLoading
    }

    private fun renderContent(isVisible: Boolean) {
        recyclerView.isVisible = isVisible
    }

    private fun updateList(inputValue: Double) {
        val list = adapter.currentList

        val newList = list.map {
            val c = if (inputValue > 0)
                it.value.calculateRate(inputValue)
            else
                0.0

            it.copy(currency = c)
        }

        adapter.submitList(newList)
    }
}
