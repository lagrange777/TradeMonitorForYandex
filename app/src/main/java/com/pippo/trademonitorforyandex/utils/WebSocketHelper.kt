package com.pippo.trademonitorforyandex.utils

import android.util.Log
import com.pippo.trademonitorforyandex.StockStorage
import com.pippo.trademonitorforyandex.datamodels.StockData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

object WebSocketHelper {

    const val WEB_SOCKET_URL = "wss://ws.finnhub.io"
    const val TOKEN = "?token=c1eaief48v6t1299ibtg"
    const val TAG = "PIPPO WS"

    private val coinbaseUri: URI? = URI(WEB_SOCKET_URL + TOKEN)
    private val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory

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
//                unsubscribeAll(StockStorage.stockSymbols.keys.toList())

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
            val adapter: JsonAdapter<StockData.StockTradeData> =
                moshi.adapter(StockData.StockTradeData::class.java)
            val stockTrade = adapter.fromJson(message)
            stockTrade?.let {
                // TODO обновление параметров через веб-сокет
            }

        }
    }

    fun subscribeAll(symbols: List<String>) {
        GlobalScope.launch(Dispatchers.IO) {
            symbols.take(10).forEach {
                Log.d( "onSubscribe", "{\"type\":\"subscribe\",\"symbol\":\"$it\"}")
                webSocketClient.send("{\"type\":\"subscribe\",\"symbol\":\"$it\"}")
                delay(100L)
            }
        }
    }

    fun subscribe(symbol: String) {
        webSocketClient.send("{\"type\":\"subscribe\",\"symbol\":\"$symbol\"}")
    }

    fun unsubscribeAll(symbols: List<String>) {
        GlobalScope.launch(Dispatchers.IO) {
            symbols.forEach {
                webSocketClient.send("{\"type\":\"unsubscribe\",\"symbol\":\"$it\"}")
                delay(10L)
            }
        }
    }

    fun unsubscribe(symbol: String) {
        webSocketClient.send("{\"type\":\"unsubscribe\",\"symbol\":\"$symbol\"}")
    }

}