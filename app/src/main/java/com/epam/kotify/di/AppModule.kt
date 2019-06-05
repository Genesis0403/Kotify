package com.epam.kotify.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.epam.kotify.api.CountryTopService
import com.epam.kotify.db.AppDatabase
import com.epam.kotify.db.TopsDao
import com.epam.kotify.utils.ConnectionManager
import com.epam.kotify.utils.Constants
import com.epam.kotify.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [TopsViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideCountryTopService(): CountryTopService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
            .databaseBuilder(app, AppDatabase::class.java, Constants.APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTopsDao(db: AppDatabase): TopsDao {
        return db.topsDao()
    }
}