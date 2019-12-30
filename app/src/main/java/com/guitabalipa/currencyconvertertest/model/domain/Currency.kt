package com.guitabalipa.currencyconvertertest.model.domain

import androidx.annotation.DrawableRes
import com.example.testcurrencies.R

enum class Currency(val desc: String, @DrawableRes val flag: Int) {
    AUD("Australian Dollar",
        R.drawable.australia_flag
    ),
    BGN("Bulgarian Lev", R.drawable.bulgary_flag),
    BRL("Brazilian Real", R.drawable.brazil_flag),
    CAD("Canadian Dollar", R.drawable.canada_flag),
    CHF("Swiss Franc", R.drawable.swiss_flag),
    CNY("Chinese Yuan", R.drawable.china_flag),
    CZK("Czech Koruna", R.drawable.czech_flag),
    DKK("Danish Krone", R.drawable.danish_flag),
    EUR("Euro", R.drawable.europe_union_flag),
    GBP("Pound Sterling", R.drawable.uk_flag),
    HKD("Hong Kong Dollar", R.drawable.hong_kong_flag),
    HRK("Croatian Kuna", R.drawable.croatia_flag),
    HUF("Hungarian Forint", R.drawable.hungary_flag),
    IDR("Indonesian Rupiah",
        R.drawable.indonesian_flag
    ),
    ILS("New Israeli Sheqel", R.drawable.israel_flag),
    INR("Indian Rupee", R.drawable.india_flag),
    ISK("Icelandic Krona", R.drawable.iceland_flag),
    JPY("Japanese Yen", R.drawable.japan_flag),
    KRW("South Korean Won",
        R.drawable.south_korea_flag
    ),
    MXN("Mexican Peso", R.drawable.mexico_flag),
    MYR("Malaysian Ringgit", R.drawable.malaysia_flag),
    NOK("Norwegian Krone", R.drawable.norway_flag),
    NZD("New Zealand Dollar",
        R.drawable.new_zealand_flag
    ),
    PHP("Philippine Peso", R.drawable.philippine_flag),
    PLN("Polish Zloty", R.drawable.poland_flag),
    RON("Romanian Leu", R.drawable.romania_flag),
    RUB("Russian Ruble", R.drawable.russia_flag),
    SEK("Swedish Krona", R.drawable.sweden_flag),
    SGD("Singapore Dollar", R.drawable.singapore_flag),
    THB("Thai Baht", R.drawable.thailand_flag),
    TRY("Turkish Lira", R.drawable.turkey_flag),
    USD("US Dollar", R.drawable.usa_flag),
    ZAR("South African Rand",
        R.drawable.south_africa_flag
    )
}
