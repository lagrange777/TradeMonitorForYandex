package com.pippo.trademonitorforyandex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pippo.trademonitorforyandex.databinding.ActivityNavigationBinding
import com.pippo.trademonitorforyandex.ui.aboutinstrument.AboutInstrumentFragment
import com.pippo.trademonitorforyandex.ui.market.MarketFragment
import com.pippo.trademonitorforyandex.utils.HTTPHelper
import com.pippo.trademonitorforyandex.utils.NavDestination
import com.pippo.trademonitorforyandex.utils.WebSocketHelper.closeWebSocketClient
import com.pippo.trademonitorforyandex.utils.WebSocketHelper.createWebSocketClient

class NavigationActivity : AppCompatActivity() {

    private val binding: ActivityNavigationBinding
        get() = ActivityNavigationBinding.inflate(layoutInflater)

    private val fragmentContainer
        get() = binding.activityNavigationContainer

    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        HTTPHelper.askStockSymbols(this)

        goTo(NavDestination.Market)
    }

    override fun onResume() {
        super.onResume()
        createWebSocketClient()
    }

    override fun onPause() {
        super.onPause()
        closeWebSocketClient()
    }

    fun goTo(dest: NavDestination) {
        val transaction = manager.beginTransaction()
        val fragment = when (dest) {
            NavDestination.Market -> MarketFragment()
            NavDestination.AboutInstrument -> AboutInstrumentFragment()
        }
        transaction.replace(fragmentContainer.id, fragment)
        transaction.commit()
    }
}