package com.pippo.trademonitorforyandex

import com.pippo.trademonitorforyandex.datamodels.StockData
import com.pippo.trademonitorforyandex.datamodels.StockPosition

/**
 * Класс-синглтон для хранения информации об акциях
 * */


object StockStorage {

    val stocks = hashMapOf<String, StockData.StockSymbol>()

    var onStockDataInit: (Unit) -> Unit = { }
    var onStockDataUpdate: (Unit) -> Unit = { }

    fun getStockInitDataEvent(onEvent: (Unit) -> Unit) {
        onStockDataInit = onEvent
    }

    fun getStockUpdateDataEvent(onEvent: (Unit) -> Unit) {
        onStockDataUpdate = onEvent
    }
}


