package com.pippo.trademonitorforyandex.ui.market

import androidx.lifecycle.ViewModel
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.Stock

class MarketViewModel : ViewModel() {

    private fun getSymbols() : HashSet<Stock.StockSymbol> {

        StockStorage.onStockSymbolUpdate {
            StockStorage.stockSymbols.mapToItems()
        }

    }

}