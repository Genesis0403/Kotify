package com.epam.kotify

import android.app.Activity
import android.app.Application
import com.epam.kotify.di.AppComponent
import com.epam.kotify.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class KotifyApp : Application(), HasActivityInjector {

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