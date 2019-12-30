@file:Suppress("NOTHING_TO_INLINE")

package com.guitabalipa.currencyconvertertest.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.guitabalipa.currencyconvertertest.di.Injectable
import com.guitabalipa.currencyconvertertest.di.bind
import org.kodein.di.Kodein
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty0

abstract class BaseViewModelActivity : AppCompatActivity(), ViewDiffContainer, Injectable {

    override val updated: HashMap<String, Any?> = HashMap()

    override val kodein: Kodein by bind()

    override fun onDestroy() {
        super.onDestroy()
        updated.clear()
    }

    protected inline infix fun <reified R> KProperty0<R>.partTo(noinline renderer: (R) -> Unit) {
        val func = renderer as KFunction<*>
        val named = "${this.name}:${func.name}"
        part(named, this.get(), renderer)
    }

    protected inline fun <T, VM: BaseViewModel<T, *>> VM.bind(crossinline state: T.() -> Unit) {
        this.state.observe(this@BaseViewModelActivity, Observer {
            if (it != null) {
                state(it)
            }
        })
    }

    protected inline fun <T, VM: BaseViewModel<*, T>> VM.listen(crossinline command: T.() -> Unit) {
        this.command.observe(this@BaseViewModelActivity, Observer {
            if (it != null) {
                command(it)
            }
        })
    }
}
