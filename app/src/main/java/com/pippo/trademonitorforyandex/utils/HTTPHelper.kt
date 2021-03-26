package com.pippo.trademonitorforyandex.utils

import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.pippo.trademonitorforyandex.App
import com.pippo.trademonitorforyandex.PippoApp
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.Stock
import org.json.JSONArray
import org.json.JSONObject

object HTTPHelper {

    const val URL = "https://finnhub.io/api/v1/stock/symbol?exchange=US&"
    const val TOKEN = "token=c1eaief48v6t1299ibtg"

    fun askStockSymbols() {
        val url = "$URL$TOKEN"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    parseStockSymbols(response)

                } catch (e: Exception) {
                    Log.d("VOLLEY EXC", "$e")
                }
            }, {
                Log.d("VOLLEY ERROR", "$it")
            })
        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10, 0, 1f)
        request.setShouldCache(false)

        PippoApp.getContext()
        App.get?.let { context ->
            VolleySingleton.getInstance(context).addToRequestQueue(request)
        }
    }

    private fun parseStockSymbols(stockSymbolsList: JSONArray) {
        for (i in 0 until stockSymbolsList.length()) {
            val stockSymbolItem = stockSymbolsList.getJSONObject(i)
            val stockSymbol = Stock.StockSymbol(
                currency = stockSymbolItem.getString("currency"),
                description = stockSymbolItem.getString("description"),
                displaySymbol = stockSymbolItem.getString("displaySymbol"),
                figi = stockSymbolItem.getString("figi"),
                mic = stockSymbolItem.getString("mic"),
                symbol = stockSymbolItem.getString("symbol"),
                type = stockSymbolItem.getString("type")
            )
            StockStorage.stockSymbols.add(stockSymbol)
        }
    }
}