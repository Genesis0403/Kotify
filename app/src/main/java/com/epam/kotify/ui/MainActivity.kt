package com.epam.kotify.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.ui.viewmodels.TopsViewModel
import com.epam.kotify.utils.ThemeManager
import javax.inject.Inject

/**
 * Main Activity which contains [ViewPager].
 *
 * @author Vlad Korotkevich
 */

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var themeManager: ThemeManager

    private lateinit var topsViewModel: TopsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setContentView(R.layout.activity_main)

        topsViewModel = ViewModelProviders.of(this, viewModelFactory)[TopsViewModel::class.java]

        themeManager.observeTheme(this)

        val toolBar = findViewById<Toolbar>(R.id.mainActivityToolBar)
        setSupportActionBar(toolBar)

        val viewPager = findViewById<ViewPager>(R.id.topsViewPager)
        viewPager.adapter = TopsViewPagerAdapter(supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.themes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}