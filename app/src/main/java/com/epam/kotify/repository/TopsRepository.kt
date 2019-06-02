package com.epam.kotify.repository

import androidx.lifecycle.LiveData
import com.epam.kotify.api.ApiResponse
import com.epam.kotify.api.ApiSuccessResponse
import com.epam.kotify.api.CountryTopService
import com.epam.kotify.model.artists.TopArtistsResponse
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.model.domain.Track
import com.epam.kotify.model.tracks.TopTracksResponse
import com.epam.kotify.utils.AppExecutors
import retrofit2.Retrofit

class TopsRepository(
    private val retrofit: Retrofit,
    private val executors: AppExecutors
) {

    fun loadCountryTopArtists(
        country: String,
        limit: Int,
        apiKey: String
    ): LiveData<Resource<List<Artist>>> {
        return object : NetworkBoundResource<List<Artist>, TopArtistsResponse>(executors) {
            override fun saveCallResult(item: TopArtistsResponse) {
                TODO("not implemented")
            }

            override fun shouldFetch(data: List<Artist>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Artist>> {
                TODO("not implemented")
            }

            override fun createCall(): LiveData<ApiResponse<TopArtistsResponse>> {
                return retrofit.create(CountryTopService::class.java).getGeoTopArtists(country, limit, apiKey)
            }

            override fun processResponse(response: ApiSuccessResponse<TopArtistsResponse>): TopArtistsResponse {
                return super.processResponse(response)
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
                TODO("not implemented")
            }

            override fun shouldFetch(data: List<Track>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Track>> {
                TODO("not implemented")
            }

            override fun createCall(): LiveData<ApiResponse<TopTracksResponse>> {
                return retrofit.create(CountryTopService::class.java).getGeoTopTracks(country, limit, apiKey)
            }

            override fun processResponse(response: ApiSuccessResponse<TopTracksResponse>): TopTracksResponse {
                return super.processResponse(response)
            }

        }.asLiveData()
    }
}