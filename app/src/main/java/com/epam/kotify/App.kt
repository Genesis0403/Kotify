package com.epam.kotify

import android.app.Activity
import android.app.Application
import com.epam.kotify.di.AppComponent
import com.epam.kotify.di.DaggerAppComponent
import com.epam.kotify.utils.AppContextProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Main application.
 *
 * @author Vlad Korotkevich
 */

class App : Application(), HasActivityInjector {

    companion object {
        private lateinit var _component: AppComponent
        val component: AppComponent get() = _component
    }

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        _component = DaggerAppComponent.builder()
            .application(this)
            .build()
        component.inject(this)
    }

    override fun activityInjector() = dispatchingActivityInjector
}