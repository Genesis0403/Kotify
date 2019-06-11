package com.epam.kotify.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.epam.kotify.App
import com.epam.kotify.R
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

    private lateinit var viewModel: TopsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        themeManager.setActivityTheme()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[TopsViewModel::class.java]

        val viewPager = findViewById<ViewPager>(R.id.topsViewPager)
        viewPager.apply {
            adapter = TopsViewPagerAdapter(supportFragmentManager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.themes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.lightTheme -> {
                if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    == Configuration.UI_MODE_NIGHT_YES
                ) {
                    themeManager.saveTheme(R.id.lightTheme)
                    recreate()
                }
                return true
            }
            R.id.darkTheme -> {
                if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    == Configuration.UI_MODE_NIGHT_NO
                ) {
                    themeManager.saveTheme(R.id.darkTheme)
                    recreate()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}