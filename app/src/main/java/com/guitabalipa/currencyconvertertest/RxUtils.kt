package com.guitabalipa.currencyconvertertest

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> applyFlowableAsync(): FlowableTransformer<T, T> {
    return FlowableTransformer { flowable ->
        flowable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
