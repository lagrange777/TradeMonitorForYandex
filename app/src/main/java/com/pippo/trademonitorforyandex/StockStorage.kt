package com.pippo.trademonitorforyandex

import com.pippo.trademonitorforyandex.datamodels.Stock

/**
 * Класс-синглтон для хранения информации об акциях
 * */


object StockStorage {

    /**
     * Множество объектов со статичной информацией об акциях
     */
    val stockSymbols: HashSet<Stock.StockSymbol> = hashSetOf()

    /**
     * Множество объектов с обновляемой информацией о текущей стоимости акции
     * В момент добавления/удаления элмента из множества срабатывает событие об изменении множества
     */
    val stockTrades: HashSet<Stock.StockTrade> = hashSetOf()

    /**
     * Лямбда, которая сигнализирует об обновлении списка
     * */
    var onStockTradeUpdate: (Unit) -> Unit = { }

    /**
     * Метод для получения события
     * */
    fun onStockTradeUpdate(onUpdate: (Unit) -> Unit) {
        onStockTradeUpdate = onUpdate
    }
}


