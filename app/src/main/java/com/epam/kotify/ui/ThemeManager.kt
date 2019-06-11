package com.epam.kotify.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.epam.kotify.R
import com.epam.kotify.utils.AppContextProvider
import javax.inject.Inject

class ThemeManager @Inject constructor(
    appContextProvider: AppContextProvider
) {

    private companion object {
        private const val THEME = "theme"
        private const val SP = "shared_preferences"
    }

    private val sharedPreferences =
        appContextProvider.context.get()?.getSharedPreferences(SP, Context.MODE_PRIVATE)

    fun saveTheme(id: Int) {
        sharedPreferences?.edit {
            putInt(THEME, id)
        }
    }

    fun getTheme() = sharedPreferences?.getInt(THEME, 0)

    fun setActivityTheme() {
        when (getTheme()) {
            R.id.lightTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.darkTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}