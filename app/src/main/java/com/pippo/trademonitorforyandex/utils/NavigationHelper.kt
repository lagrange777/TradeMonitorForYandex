package com.pippo.trademonitorforyandex.utils

sealed class NavDestination {
    object Market : NavDestination()
    object AboutInstrument : NavDestination()
}