package com.epam.kotify.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epam.kotify.ui.viewmodels.TopsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module which binds ViewModel's and Factory fo them.
 *
 * @see AppModule
 * @author Vlad Korotkevich
 */

@Module
abstract class TopsViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: TopsViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TopsViewModel::class)
    abstract fun provideTopsViewModel(viewModel: TopsViewModel): ViewModel
}