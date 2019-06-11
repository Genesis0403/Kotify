package com.epam.kotify.api

import androidx.lifecycle.LiveData
import com.epam.kotify.model.artists.TopArtistsResponse
import com.epam.kotify.model.tracks.TopTracksResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API requests.
 *
 * @author Vlad Korotkevich
 */

interface CountryTopService {

    @GET("?method=geo.gettopartists&format=json")
    fun getGeoTopArtists(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponse<TopArtistsResponse>>

    @GET("?method=geo.gettoptracks&format=json")
    fun getGeoTopTracks(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponse<TopTracksResponse>>
}