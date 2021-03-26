package com.pippo.trademonitorforyandex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pippo.trademonitorforyandex.R
import com.pippo.trademonitorforyandex.databinding.ActivityNavigationBinding
import com.pippo.trademonitorforyandex.datamodels.Instrument
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class NavigationActivity : AppCompatActivity() {

    val coinbaseUri: URI? = URI(WEB_SOCKET_URL)

    val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory

    private val binding: ActivityNavigationBinding
        get() = ActivityNavigationBinding.inflate(layoutInflater)

    private val btcPrice
        get() = binding.btcTv


    private lateinit var webSocketClient: WebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initWS()
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    private fun initWS() {
        createWebSocketClient(coinbaseUri)
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {

        webSocketClient = object : WebSocketClient(coinbaseUri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                setUpBtcPriceText(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                unsubscribe()

            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }

        }
        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    private fun subscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                    "}"
        )
    }

    private fun unsubscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"unsubscribe\",\n" +
                    "    \"channels\": [\"ticker\"]\n" +
                    "}"
        )
    }

    private fun setUpBtcPriceText(message: String?) {
        message?.let {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<Instrument.BitcoinTicker> =
                moshi.adapter(Instrument.BitcoinTicker::class.java)
            val bitcoin = adapter.fromJson(message)

            btcPrice.text = "1 BTC: ${bitcoin?.price} €"

        }
    }

    companion object {
        const val WEB_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"
        const val TAG = "Coinbase"
    }
}