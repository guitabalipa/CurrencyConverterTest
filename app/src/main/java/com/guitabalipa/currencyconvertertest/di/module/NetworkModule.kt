package com.guitabalipa.currencyconvertertest.di.module

import com.guitabalipa.currencyconvertertest.api.RateApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val HOST = "https://revolut.duckdns.org/"

val networkModule = Kodein.Module("Network Module") {

    bind<Moshi>() with singleton {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    bind<Converter.Factory>() with singleton {
        MoshiConverterFactory.create(instance())
            .withNullSerialization()
    }

    bind<HttpLoggingInterceptor>() with singleton {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
            .connectTimeout(130, TimeUnit.SECONDS)
            .writeTimeout(130, TimeUnit.SECONDS)
            .readTimeout(130, TimeUnit.SECONDS)
            .callTimeout(130, TimeUnit.SECONDS)
            .addInterceptor(instance<HttpLoggingInterceptor>())
            .build()
    }

    bind<Retrofit>() with factory { host: String ->
        Retrofit.Builder()
            .baseUrl(host)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(instance())
            .client(instance())
            .build()
    }

    bind<RateApi>() with singleton {
        instance<String, Retrofit>(arg = HOST)
            .create(RateApi::class.java)
    }
}
