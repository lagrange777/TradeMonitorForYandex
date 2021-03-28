package com.pippo.trademonitorforyandex.utils

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    private var pref: SharedPreferences? = null

    private val APP_PREFERENCES_USER = "user"


    fun getFavs(context: Context) {
        pref = context.getSharedPreferences(APP_PREFERENCES_USER, Context.MODE_PRIVATE)



    }
}
