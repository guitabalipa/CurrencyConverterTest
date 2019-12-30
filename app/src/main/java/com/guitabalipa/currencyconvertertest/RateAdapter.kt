package com.guitabalipa.currencyconvertertest

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testcurrencies.R
import com.guitabalipa.currencyconvertertest.RateAdapter.Companion.PAYLOAD_CURRENCY
import com.guitabalipa.currencyconvertertest.RateAdapter.Companion.PAYLOAD_HAS_FOCUS
import com.guitabalipa.currencyconvertertest.model.domain.Rate
import kotlinx.android.synthetic.main.currency_item.view.*
import java.text.NumberFormat
import kotlin.properties.Delegates

class RateAdapter(
    private val context: Context,
    private val onClickItem: (String, Double) -> Unit,
    private val onTextUpdated: (Double) -> Unit
) : ListAdapter<Rate, RateViewHolder>(
    DIFF_UTIL
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.currency_item,
                parent,
                false
            ),
            onClickItem,
            onTextUpdated
        )
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onBindViewHolder(
        holder: RateViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty())
            holder.bindTo(getItem(position), payloads[0] as Bundle)
        else
            super.onBindViewHolder(holder, position, payloads)
    }

    companion object {

        const val PAYLOAD_CURRENCY = "payload_currency"
        const val PAYLOAD_HAS_FOCUS = "payload_has_focus"

        val DIFF_UTIL = object : DiffUtil.ItemCallback<Rate>() {
            override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
                return oldItem.currency == newItem.currency && oldItem.hasFocus == newItem.hasFocus
            }

            override fun getChangePayload(oldItem: Rate, newItem: Rate): Any? {
                return Bundle().apply {
                    if (oldItem.currency != newItem.currency)
                        putDouble(PAYLOAD_CURRENCY, newItem.currency)

                    if (oldItem.hasFocus != newItem.hasFocus)
                        putBoolean(PAYLOAD_HAS_FOCUS, newItem.hasFocus)
                }
            }
        }
    }
}

class RateViewHolder(
    itemView: View,
    private val onClickItem: (String, Double) -> Unit,
    private val onTextUpdated: (Double) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val format: NumberFormat = NumberFormat.getNumberInstance()

    private var currentInput: Double by Delegates.notNull()

    private var valueTextWatcher: TextWatcher? = null

    init {
        format.maximumFractionDigits = 2
    }

    fun bindTo(item: Rate) {
        itemView.editText.setOnClickListener { onClickItem.invoke(item.name, currentInput) }
        itemView.setOnClickListener { onClickItem.invoke(item.name, currentInput) }

        currentInput = item.currency

        removeFocus()
        addCurrencyTextWatcher()

        itemView.textView.text = item.name
        itemView.textView2.text = item.desc
        itemView.imageViewFlag.setImageResource(item.flag)

        setCurrency(item.currency)

        if (adapterPosition == 0) {
            addFocus()
        }
    }

    fun bindTo(item: Rate, payload: Bundle) {
        payload.keySet().forEach {
            if (it == PAYLOAD_CURRENCY && adapterPosition != 0) {
                setCurrency(item.currency)
            }

            if (it == PAYLOAD_HAS_FOCUS) {
                if (item.hasFocus) {
                    addFocus()
                } else {
                    removeFocus()
                }
            }
        }
    }

    private fun setCurrency(currency: Double) {
        currentInput = currency
        if (currentInput > 0)
            itemView.editText.setText(Mask.formatCurrency(currentInput))
        else
            itemView.editText.text = null
    }

    private fun addFocus() {
        itemView.editText.isFocusable = true
        itemView.editText.isFocusableInTouchMode = true
        itemView.editText.requestFocus()
    }

    private fun removeFocus() {
        itemView.editText.isFocusable = false
        itemView.editText.isFocusableInTouchMode = false
        itemView.editText.clearFocus()
    }

    private fun addCurrencyTextWatcher() {
        valueTextWatcher = Mask.createCurrencyValueChangedListener(itemView.editText) {
            if (adapterPosition == 0)
                onTextUpdated.invoke(it)
        }
        itemView.editText.addTextChangedListener(valueTextWatcher)
    }

    private fun removeTextWatcher() {
        itemView.editText.removeTextChangedListener(valueTextWatcher)
        valueTextWatcher = null
    }
}
