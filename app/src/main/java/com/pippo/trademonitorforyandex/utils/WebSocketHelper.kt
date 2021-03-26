package com.pippo.trademonitorforyandex.utils

import android.util.Log
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.Stock
import com.pippo.trademonitorforyandex.ui.NavigationActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import javax.net.ssl.SSLSocketFactory

object WebSocketHelper {

    const val WEB_SOCKET_URL = "wss://ws.finnhub.io"
    const val TOKEN = "?token=c1eaief48v6t1299ibtg"
    const val TAG = "Coinbase"

    private val coinbaseUri: URI? = URI(WEB_SOCKET_URL + TOKEN)
    private  val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory

    private lateinit var webSocketClient: WebSocketClient

    fun createWebSocketClient() {

        webSocketClient = object : WebSocketClient(coinbaseUri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                processMessage(message)

            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                unsubscribeAll()

            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }

        }
        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    fun closeWebSocketClient() {
        webSocketClient.close()
    }

    private fun processMessage(message: String?) {
        message?.let {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<Stock.StockTrade> =
                moshi.adapter(Stock.StockTrade::class.java)
            val stockTrade = adapter.fromJson(message)
            stockTrade?.let {
                StockStorage.stockTrades.add(stockTrade)
                StockStorage.onStockTradeUpdate.invoke(Unit)
            }

        }
    }

    fun subscribe(symbol: String) {
        webSocketClient.send("{\"type\":\"subscribe\",\"symbol\":\"$symbol\"}")
    }

    fun unsubscribe(symbol: String) {
        webSocketClient.send("{\"type\":\"unsubscribe\",\"symbol\":\"$symbol\"}")
    }

    fun unsubscribeAll() {
        webSocketClient.send("{\"type\":\"unsubscribe\",\"symbol\":\"ALL\"}")
    }

}