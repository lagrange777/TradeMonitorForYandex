package com.pippo.trademonitorforyandex.datamodels

import android.graphics.drawable.Drawable
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BitcoinTicker(val price: String?)

@JsonClass(generateAdapter = true)
open class StockData {

    @JsonClass(generateAdapter = true)
    data class StockSymbol(
        val currency: String,
        val description: String,
        val displaySymbol: String,
        val figi: String,
        val mic: String,
        val symbol: String,
        val type: String,
        var isFav: Boolean = false,
        var tradeData: StockTradeData? = null
    ) : StockData()

    /**
     * @param p - last price
     * @param t - update time (UNIX milliseconds timestamp)
     * @param v - volume
     * @param o - open price of the day
     * @param h - high price of the day
     * @param l - low price of the day
     * @param c - current price
     * @param pc - previous close price
     * */
    @JsonClass(generateAdapter = true)
    data class StockTradeData(
        var p: Double = 0.0,
        val t: Long = 0L,
        var v: Double = 0.0,
        var o: Double = 0.0,
        var h: Double = 0.0,
        var l: Double = 0.0,
        var c: Double = 0.0,
        var pc: Double = 0.0
    ): StockData()
}

@JsonClass(generateAdapter = true)
data class StockPosition(
    var symbol: StockData.StockSymbol
)