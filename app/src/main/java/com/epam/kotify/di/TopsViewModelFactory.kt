package com.epam.kotify.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModel's factory which is used to create ViewModel's.
 * Uses [MutableMap] generated by Dagger.
 *
 * @author Vlad Korotkevich
 */

class TopsViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModels[modelClass]
            ?: throw IllegalArgumentException("Model class $modelClass not found.")
        return viewModelProvider.get() as T
    }
}