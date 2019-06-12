package com.epam.kotify.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import com.epam.kotify.R
import javax.inject.Inject

class NightThemeLiveData @Inject constructor(
    contextProvider: AppContextProvider
) : LiveData<Boolean>() {

    private val sharedPrefs =
        PreferenceManager.getDefaultSharedPreferences(
            contextProvider.context()
        )

    private val prefKey =
        contextProvider.getString(R.string.pref_night_theme_key)

    private val onSharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener { sp, key ->
        if (key == prefKey) {
            postValue(sp.getBoolean(prefKey, false))
        }
    }

    override fun onActive() {
        super.onActive()
        postValue(sharedPrefs.getBoolean(prefKey, false))
        sharedPrefs.registerOnSharedPreferenceChangeListener(onSharedPrefsListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(onSharedPrefsListener)
    }
}