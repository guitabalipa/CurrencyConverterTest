package com.guitabalipa.currencyconvertertest

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object Mask {

    fun createCurrencyValueChangedListener(editText: EditText, action: ((Double) -> Unit)?): TextWatcher {
        return object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString() != current) {
                    editText.removeTextChangedListener(this)

                    var cleanString = editable.toString().replace("[,.\\s]".toRegex(), "")

                    if (cleanString.length >= 15) {
                        cleanString = cleanString.substring(0, cleanString.length - 1)
                    }

                    val parsed = if (cleanString.isEmpty()) 0.0 else cleanString.toDouble()

                    val formatted =
                        formatCurrency(
                            parsed / 100
                        )

                    current = formatted

                    val filters = editable.filters
                    editable.filters = arrayOf()
                    editable.clear()
                    editable.append(current)
                    editable.filters = filters

                    action?.invoke(parsed / 100.0)

                    editText.addTextChangedListener(this)
                }
            }
        }
    }

    private fun getCurrencyInstance(): NumberFormat {
        return DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale.getDefault()))
    }

    fun formatCurrency(price: Double): String {
        return getCurrencyInstance().format(price)
    }
}
