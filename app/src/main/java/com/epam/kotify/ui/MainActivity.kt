package com.epam.kotify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.epam.kotify.KotifyApp
import com.epam.kotify.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TopsViewModel

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KotifyApp.component.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[TopsViewModel::class.java]

        val viewPager = findViewById<ViewPager>(R.id.topsViewPager)
        viewPager.apply {
            adapter = TopsViewPagerAdapter(
                supportFragmentManager,
                viewModel.fragments,
                viewModel.pagesNames
            )
        }
    }

    override fun supportFragmentInjector() = fragmentInjector
}
