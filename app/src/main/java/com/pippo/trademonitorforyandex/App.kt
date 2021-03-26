package com.pippo.trademonitorforyandex

import android.app.Application
import android.content.Context
import com.pippo.trademonitorforyandex.utils.WebSocketHelper

class PippoApp : Application() {

    companion object {
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }

}