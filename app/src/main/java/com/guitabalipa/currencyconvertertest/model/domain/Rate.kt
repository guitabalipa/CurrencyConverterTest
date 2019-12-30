package com.guitabalipa.currencyconvertertest.model.domain

data class Rate(
    val flag: Int,
    val name: String,
    val desc: String,
    val value: Double,
    val currency: Double,
    val hasFocus: Boolean
)

fun Double.calculateRate(inputValue: Double): Double {
    return inputValue * this
}
