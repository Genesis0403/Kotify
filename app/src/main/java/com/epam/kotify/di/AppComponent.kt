package com.epam.kotify.di

import android.app.Application
import com.epam.kotify.App
import com.epam.kotify.ui.*
import com.epam.kotify.ui.artistview.LovedArtistsFragment
import com.epam.kotify.ui.artistview.TopArtistsFragment
import com.epam.kotify.ui.tracksview.LovedTracksFragment
import com.epam.kotify.ui.tracksview.TopTracksFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * App component which is used to inject all dependencies.
 * Also binds application context to it.
 *
 * @see AppModule
 * @author Vlad Korotkevich
 */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(activity: MainActivity)
    fun inject(fragment: TopArtistsFragment)
    fun inject(fragment: TopTracksFragment)
    fun inject(fragment: MapFragment)
    fun inject(activity: SettingsActivity)
    fun inject(fragment: AddToLovedDialog)
    fun inject(fragment: LovedArtistsFragment)
    fun inject(fragment: LovedTracksFragment)
    fun inject(fragment: RemoveFromLovedDialog)
}