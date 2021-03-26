package com.pippo.trademonitorforyandex.datamodels

import android.graphics.drawable.Drawable
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BitcoinTicker(val price: String?)

@JsonClass(generateAdapter = true)
sealed class Stock {

    @JsonClass(generateAdapter = true)
    data class StockSymbol(
        val currency: String,
        val description: String,
        val displaySymbol: String,
        val figi: String,
        val mic: String,
        val symbol: String,
        val type: String
    ) : Stock()

    @JsonClass(generateAdapter = true)
    data class StockTrade(
        val type: String,
        val data: StockTradeData
    ) : Stock()

    @JsonClass(generateAdapter = true)
    data class StockTradeData(
        val s: String,
        val p: Double,
        val t: Long,
        val v: Double
    ) : Stock()
}