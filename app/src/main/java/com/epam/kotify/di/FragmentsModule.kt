package com.epam.kotify.di

import com.epam.kotify.ui.MapFragment
import com.epam.kotify.ui.artistview.TopArtistsFragment
import com.epam.kotify.ui.tracksview.TopTracksFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module which is used to inject fragments.
 *
 * @author Vlad Korotkevich
 */

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeTopArtistsFragment(): TopArtistsFragment

    @ContributesAndroidInjector
    abstract fun contributeTopTracksFragment(): TopTracksFragment

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment
}