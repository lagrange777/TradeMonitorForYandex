package com.pippo.trademonitorforyandex.utils

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.pippo.trademonitorforyandex.PippoApp
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.StockData
import org.json.JSONArray
import org.json.JSONObject

object HTTPHelper {

    const val BASE_URL = "https://finnhub.io/api/v1/"
    const val SYMBOL_LIST = "stock/symbol?exchange=US&mic=XASE&currency=USD"
    const val SYMBOL_LAST_PARAM = "quote?symbol="
    const val TOKEN = "&token=c1eaief48v6t1299ibtg"

    fun askStockSymbols(context: Context) {
        val url = "$BASE_URL$SYMBOL_LIST$TOKEN"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("MILILOG 1", "$response")
                    parseStockSymbols(response, context)
                } catch (e: Exception) {
                    Log.d("MILILOG EXC 1", "$e")
                }
            }, {
                Log.d("MILILOG ER 1", "$it")
            })
        request.retryPolicy =
            DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10,
                0,
                1f
            )
        request.setShouldCache(false)
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    fun askLastSymbolData(context: Context, symbol: String) {
        val url = "$BASE_URL$SYMBOL_LAST_PARAM$symbol$TOKEN"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("MILILOG 2", "$response")
                    parseSymbolParams(response, symbol)
                } catch (e: Exception) {
                    Log.d("MILILOG EXC 2", "$e")
                }
            }, {
                Log.d("MILILOG ER 2", "$it")
            })
        request.retryPolicy =
            DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10,
                0,
                1f
            )
        request.setShouldCache(false)
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    private fun parseStockSymbols(stockSymbolsList: JSONArray, context: Context) {
        val favs = PrefsHelper.getFavs(context)
        for (i in 0 until stockSymbolsList.length()) {
            val stockSymbolItem = stockSymbolsList.getJSONObject(i)
            val stockSymbol = StockData.StockSymbol(
                currency = stockSymbolItem.getString("currency"),
                description = stockSymbolItem.getString("description"),
                displaySymbol = stockSymbolItem.getString("displaySymbol"),
                figi = stockSymbolItem.getString("figi"),
                mic = stockSymbolItem.getString("mic"),
                symbol = stockSymbolItem.getString("symbol"),
                type = stockSymbolItem.getString("type"),
                isFav = stockSymbolItem.getString("symbol") in favs
            )
            StockStorage.stocks[stockSymbol.symbol] = stockSymbol
        }
        StockStorage.onStockDataInit.invoke(Unit)
    }

    private fun parseSymbolParams(params: JSONObject, symbol: String) {
        StockStorage.stocks[symbol]?.let { stock ->
            stock.tradeData = StockData.StockTradeData(
                c = params.getDouble("c"),
                h = params.getDouble("h"),
                l = params.getDouble("l"),
                o = params.getDouble("o"),
                pc = params.getDouble("pc"),
                t = params.getLong("t")
            )
        }
        StockStorage.onStockDataUpdate.invoke(Unit)
    }
}