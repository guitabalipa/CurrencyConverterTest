@file:Suppress("NOTHING_TO_INLINE")

package com.guitabalipa.currencyconvertertest.di

import android.app.Service
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.LazyKodein
import org.kodein.di.android.closestKodein
import org.kodein.di.android.subKodein
import org.kodein.di.android.x.closestKodein

inline fun KodeinAware.bind(crossinline bindings: Kodein.MainBuilder.() -> Unit = {}): LazyKodein {
    return when (this) {
        is FragmentActivity -> subKodein<FragmentActivity>(closestKodein()) {
            // Creates a subKodein for each activity extending the main kodein
            bindings.invoke(this)
        }
        is Fragment -> subKodein<Fragment>(closestKodein()) {
            // Creates a subKodein for each fragment extending from Activity
            bindings.invoke(this)
        }
        is Service -> subKodein<Service>(closestKodein()) {
            // Creates a subKodein for each service extending the main kodein
            bindings.invoke(this)
        }
        else -> throw IllegalStateException("Bind not implemented for this class type ${this::class.java.canonicalName}")
    }
}
