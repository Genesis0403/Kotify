package com.epam.kotify.utils

import android.net.ConnectivityManager

/**
 * Class which checks connection.
 *
 * @author Vlad Korotkevich
 */

data class ConnectionManager(
    private val connectivityManager: ConnectivityManager
) {

    fun hasConnection() = connectivityManager.activeNetworkInfo?.isConnected != null
}