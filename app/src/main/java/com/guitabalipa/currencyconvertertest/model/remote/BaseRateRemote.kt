package com.guitabalipa.currencyconvertertest.model.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseRateRemote(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
