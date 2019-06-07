package com.epam.kotify.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.epam.kotify.api.ApiResponse
import com.epam.kotify.api.CountryTopService
import com.epam.kotify.db.AppDatabase
import com.epam.kotify.db.TopsDao
import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.artists.TopArtistsResponse
import com.epam.kotify.model.tracks.TopTracksResponse
import com.epam.kotify.model.tracks.Track
import com.epam.kotify.utils.AppExecutors
import com.epam.kotify.utils.ConnectionManager
import javax.inject.Inject

class TopsRepository @Inject constructor(
    private val executors: AppExecutors,
    private val db: AppDatabase,
    private val topsDao: TopsDao,
    private val countryTopService: CountryTopService,
    private val connectionManager: ConnectionManager
) {

    private companion object {
        private const val TAG = "TOPS REPOSITORY"
    }

    fun loadCountryTopArtists(
        country: String,
        limit: Int,
        apiKey: String
    ): LiveData<Resource<List<Artist>>> {
        return object : NetworkBoundResource<List<Artist>, TopArtistsResponse>(executors) {
            override fun saveCallResult(item: TopArtistsResponse) {
                Log.d(TAG, "SAVING")
                val items = item.topArtists?.artists ?: emptyList()
                db.runInTransaction {
                    topsDao.clearArtists()
                    topsDao.insertArtists(items)
                }
            }

            override fun shouldFetch(data: List<Artist>?): Boolean {
                Log.d(TAG, "SHOULD FETCH?")
                return connectionManager.hasConnection()
            }

            override fun loadFromDb(): LiveData<List<Artist>> {
                Log.d(TAG, "LOAD FROM DB")
                return topsDao.getTopArtists()
            }

            override fun createCall(): LiveData<ApiResponse<TopArtistsResponse>> {
                Log.d(TAG, "MAKE CALL")
                return countryTopService.getGeoTopArtists(country, limit, apiKey)
            }
        }.asLiveData()
    }


    fun loadCountryTopTracks(
        country: String,
        limit: Int,
        apiKey: String
    ): LiveData<Resource<List<Track>>> {
        return object : NetworkBoundResource<List<Track>, TopTracksResponse>(executors) {
            override fun saveCallResult(item: TopTracksResponse) {
                val items = item.topTracks?.tracks ?: emptyList()
                db.runInTransaction {
                    topsDao.apply {
                        clearTracks()
                        insertTracks(items)
                    }
                }
            }

            override fun shouldFetch(data: List<Track>?): Boolean {
                return connectionManager.hasConnection()
            }

            override fun loadFromDb(): LiveData<List<Track>> {
                return topsDao.getTopTracks()
            }

            override fun createCall(): LiveData<ApiResponse<TopTracksResponse>> {
                return countryTopService.getGeoTopTracks(country, limit, apiKey)
            }

        }.asLiveData()
    }
}