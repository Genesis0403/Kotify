package com.epam.kotify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.preference.PreferenceFragmentCompat
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.utils.ThemeManager
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setContentView(R.layout.settings_activity)

        val toolBar = findViewById<Toolbar>(R.id.settingsToolBar)
        setSupportActionBar(toolBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.prefsContainer, SettingsFragment())
            .commit()

        themeManager.observeTheme(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }
}