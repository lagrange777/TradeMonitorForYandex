package com.pippo.trademonitorforyandex

import android.app.Application
import android.content.Context

class PippoApp : Application() {

//    private var ctx: Context? = null
//
//    companion object {
//        fun getContext() : Context? = ctx
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        ctx = this
//    }

    private var sApplication: Application? = null

    fun getApplication(): Application? {
        return sApplication
    }

    fun getContext(): Context? {
        return getApplication()!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}