@file:Suppress("NOTHING_TO_INLINE")

package com.guitabalipa.currencyconvertertest.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<S, C> : ViewModel() {

    var disposables = CompositeDisposable()

    val command = SingleLiveEvent<C>()
    val state = MutableLiveData<S>()

    protected inline fun newState(state: S) {
        this.state.value = state
    }

    protected inline fun withState(stateBlock: S.() -> Unit) {
        stateBlock(currentState())
    }

    inline fun currentState(): S {
        return state.value!!
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun remove(disposable: Disposable) {
        disposables.remove(disposable)
    }

    fun add(disposable: Disposable) {
        disposables.add(disposable)
    }
}
