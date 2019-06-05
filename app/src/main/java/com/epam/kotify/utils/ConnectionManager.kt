package com.epam.kotify.utils

import android.net.ConnectivityManager

data class ConnectionManager(
    private val connectivityManager: ConnectivityManager
) {

    fun hasConnection() = connectivityManager.activeNetworkInfo?.isConnected != null
}