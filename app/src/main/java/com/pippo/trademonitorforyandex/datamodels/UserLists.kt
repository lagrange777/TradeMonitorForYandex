package com.pippo.trademonitorforyandex.datamodels

sealed class UserData {
    data class FavoriteInstrument(val ticker: String) : UserData()
}