package com.epam.kotify.utils

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    contextProvider: AppContextProvider
) {

    val themeLiveData = NightThemeLiveData(contextProvider)

    fun observeTheme(activity: AppCompatActivity) {
        themeLiveData.observeForever(Observer<Boolean> { isNight ->
            changeTheme(activity, isNight)
        })
    }

    private fun changeTheme(activity: AppCompatActivity, isNight: Boolean) {
        if (isNight) {
            if (activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                == Configuration.UI_MODE_NIGHT_NO
            ) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activity.recreate()
            }
        } else {
            if (activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                == Configuration.UI_MODE_NIGHT_YES
            ) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                activity.recreate()
            }
        }
    }
}