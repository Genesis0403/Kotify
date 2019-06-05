package com.epam.kotify.di

import android.app.Application
import com.epam.kotify.KotifyApp
import com.epam.kotify.ui.MainActivity
import com.epam.kotify.ui.artistview.TopArtistsFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

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

    fun inject(app: KotifyApp)
    fun inject(fragment: TopArtistsFragment)
}