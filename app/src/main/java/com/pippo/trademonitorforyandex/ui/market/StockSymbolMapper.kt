package com.pippo.trademonitorforyandex.ui.market

data class StockListItem(
    val ticker: String,
    val company: String,
    val price: Double,
    val profitloss: Double
)