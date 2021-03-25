package com.pippo.trademonitorforyandex.datamodels

import android.graphics.drawable.Drawable

sealed class Instrument {

    data class Stock(val ticker: String) : Instrument() {
        var fullName: String = ""
        var shortName: String = ""
        var icon: Drawable? = null
        var currentPrice: Double = 0.0
        var profitloss: Double = 0.0
        var profitlossPercent: Double = 0.0
        var lastPrice: Double = 0.0
        var bid: Double = 0.0
        var offer: Double = 0.0
        var prevPrice: Double = 0.0
    }
}