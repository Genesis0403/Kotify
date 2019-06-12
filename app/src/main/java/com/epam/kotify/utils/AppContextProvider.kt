package com.epam.kotify.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Interface which provides context.
 * Used in injection.
 *
 * @author Vlad Korotkevich
 */

interface AppContextProvider {
    fun getString(id: Int): String?
    fun sharedPreferences(name: String, mode: Int): SharedPreferences
    fun context(): Context
}