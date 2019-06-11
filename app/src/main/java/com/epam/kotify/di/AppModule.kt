package com.epam.kotify.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import androidx.room.Room
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.api.CountryTopService
import com.epam.kotify.db.AppDatabase
import com.epam.kotify.db.TopsDao
import com.epam.kotify.utils.AppContextProvider
import com.epam.kotify.utils.ConnectionManager
import com.epam.kotify.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Singleton

/**
 * App module which helps in injection by providing all necessary dependencies.
 *
 * @see TopsViewModelModule
 * @author Vlad Korotkevich
 */

private const val NO_STRING_RESOURCE = "String not found in string resources."

@Module(
    includes = [
        TopsViewModelModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideCountryTopService(): CountryTopService {
        return Retrofit.Builder()
            .baseUrl(
                App.getString(R.string.baseUrl)
                    ?: throw IllegalStateException(NO_STRING_RESOURCE)
            )
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(CountryTopService::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectionManager(app: Application): ConnectionManager {
        val connectionManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectionManager(connectionManager)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(
                app, AppDatabase::class.java, App.getString(R.string.database)
                    ?: throw IllegalStateException(NO_STRING_RESOURCE)
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTopsDao(db: AppDatabase): TopsDao {
        return db.topsDao()
    }

    @Singleton
    @Provides
    fun provideAppContextProvider(app: Application): AppContextProvider {
        return AppContextProvider(WeakReference(app.applicationContext))
    }

    @Singleton
    @Provides
    fun provideGeocoder(app: Application): Geocoder {
        return Geocoder(app.applicationContext, Locale.US)
    }
}