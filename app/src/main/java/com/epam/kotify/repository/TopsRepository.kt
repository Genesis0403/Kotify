package com.epam.kotify.repository

import androidx.lifecycle.LiveData
import com.epam.kotify.api.ApiResponse
import com.epam.kotify.api.ApiSuccessResponse
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

/**
 * Repository which takes care of API requests.
 * Based on [NetworkBoundResource] which caches all requests and makes tham
 * if only connection is established.
 *
 * @see NetworkBoundResource
 * @see AppDatabase
 * @see TopsDao
 * @see CountryTopService
 *
 * @author Vlad Korotkevich
 */

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
            override fun processResponse(response: ApiSuccessResponse<TopArtistsResponse>): List<Artist> {
                return response.body.topArtists?.artists ?: emptyList()
            }

            override fun shouldFetch(): Boolean {
                return connectionManager.hasConnection()
            }

            override fun createCall(): LiveData<ApiResponse<TopArtistsResponse>> {
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
            override fun processResponse(response: ApiSuccessResponse<TopTracksResponse>): List<Track> {
                return response.body.topTracks?.tracks ?: emptyList()
            }

            override fun shouldFetch(): Boolean {
                return connectionManager.hasConnection()
            }

            override fun createCall(): LiveData<ApiResponse<TopTracksResponse>> {
                return countryTopService.getGeoTopTracks(country, limit, apiKey)
            }
        }.asLiveData()
    }
}