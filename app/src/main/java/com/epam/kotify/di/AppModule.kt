package com.epam.kotify.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
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
    fun provideCountryTopService(app: Application): CountryTopService {
        return Retrofit.Builder()
            .baseUrl(
                app.getString(R.string.baseUrl)
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
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return object : ConnectionManager {
            override fun hasConnection(): Boolean {
                return connectivityManager.activeNetworkInfo?.isConnected != null
            }
        }
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(
                app, AppDatabase::class.java, app.getString(R.string.database)
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
        return object : AppContextProvider {
            override fun context(): Context {
                return app
            }

            override fun getString(id: Int): String? {
                return app.getString(id)
            }

            override fun sharedPreferences(name: String, mode: Int): SharedPreferences {
                return app.getSharedPreferences(name, mode)
            }
        }
    }

    @Singleton
    @Provides
    fun provideGeocoder(app: Application): Geocoder {
        return Geocoder(app.applicationContext, Locale.US)
    }
}