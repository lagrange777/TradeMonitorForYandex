package com.pippo.trademonitorforyandex.ui.market

import com.pippo.trademonitorforyandex.datamodels.Stock

data class StockListItem(
    val ticker: String,
    val company: String,
    val price: Double,
    val profitloss: Double
)