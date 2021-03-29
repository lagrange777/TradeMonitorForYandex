package com.pippo.trademonitorforyandex.ui.market

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.StockData
import com.pippo.trademonitorforyandex.utils.PrefsHelper
import com.pippo.trademonitorforyandex.utils.WebSocketHelper
import java.util.*

class MarketViewModel : ViewModel() {

    val stockLD = MutableLiveData<List<StockData.StockSymbol>>()
    var searchFilter: (StockData.StockSymbol) -> Boolean = { true }
    var onlyFavs = false
    private var currentFavs = setOf<String>()

    private val allStocks
        get() = StockStorage.stocks.values.toList()

    private val currentList
        get() = allStocks.filter(searchFilter).filter { if (onlyFavs) it.isFav else true }


    init {
        StockStorage.getStockUpdateDataEvent {
            stockLD.postValue(currentList)
        }
    }

    fun getFilteredStocks() = currentList

    fun addToFav(ticker: String, context: Context){
        val currentFavs = PrefsHelper.getFavs(context)
        if (ticker in currentFavs) {
            PrefsHelper.removeFav(ticker, context)
            StockStorage.stocks[ticker]?.isFav = false
        } else {
            PrefsHelper.addFav(ticker, context)
            StockStorage.stocks[ticker]?.isFav = true
        }
        this.currentFavs = PrefsHelper.getFavs(context)
        stockLD.postValue(currentList)
    }

    private fun String.isFav() = this in currentFavs
}