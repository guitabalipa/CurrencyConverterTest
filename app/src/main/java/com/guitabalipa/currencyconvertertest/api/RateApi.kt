package com.guitabalipa.currencyconvertertest.api

import com.guitabalipa.currencyconvertertest.model.remote.BaseRateRemote
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface RateApi {

    @GET("latest")
    fun getRates(@Query("base") base: String): Flowable<BaseRateRemote>
}
