package com.epam.kotify.di

import com.epam.kotify.ui.artistview.TopArtistsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeTopArtistsFragment(): TopArtistsFragment
}