package com.epam.kotify.di

import com.epam.kotify.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentsModule::class])
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity
}