package com.pippo.trademonitorforyandex.utils

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    const val FAVORITE_STOCK_KEY = "favstock"
    const val APP_PREFERENCES_USER = "user"
    private var pref: SharedPreferences? = null


    fun getFavs(context: Context): Set<String> {
        pref = context.getSharedPreferences(APP_PREFERENCES_USER, Context.MODE_PRIVATE)
        return pref?.getStringSet(FAVORITE_STOCK_KEY, setOf()) ?: setOf()
    }

    fun addFav(ticker: String, context: Context) {
        pref = context.getSharedPreferences(APP_PREFERENCES_USER, Context.MODE_PRIVATE)
        pref?.let {
            val currentFavs = it.getStringSet(FAVORITE_STOCK_KEY, mutableSetOf()) ?: mutableSetOf()
            currentFavs.add(ticker)
            val editor = it.edit()
            editor.putStringSet(FAVORITE_STOCK_KEY, currentFavs)
            editor.apply()
        }
    }

    fun removeFav(ticker: String, context: Context) {
        pref = context.getSharedPreferences(APP_PREFERENCES_USER, Context.MODE_PRIVATE)
        pref?.let {
            val currentFavs = it.getStringSet(FAVORITE_STOCK_KEY, mutableSetOf()) ?: mutableSetOf()
            currentFavs.remove(ticker)
            val editor = it.edit()
            editor.putStringSet(FAVORITE_STOCK_KEY, currentFavs)
            editor.apply()
        }
    }
}
