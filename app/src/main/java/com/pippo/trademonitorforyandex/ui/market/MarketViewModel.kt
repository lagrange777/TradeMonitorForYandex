package com.pippo.trademonitorforyandex.ui.market

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.StockData
import com.pippo.trademonitorforyandex.utils.WebSocketHelper
import java.util.*

class MarketViewModel : ViewModel() {

    val stockLD = MutableLiveData<List<StockData.StockSymbol>>()
    var searchFilter: (StockData.StockSymbol) -> Boolean = { true }

    private var allStocks =  listOf<StockData.StockSymbol>()

    private val currentList
        get() = allStocks.filter(searchFilter)


    init {
        StockStorage.getStockUpdateDataEvent {
            allStocks = StockStorage.stocks.values.toList()
            stockLD.postValue(currentList)
        }
    }

    fun getFilteredStocks() = allStocks.filter(searchFilter)

    fun addToFav(ticker: String) {

    }
}