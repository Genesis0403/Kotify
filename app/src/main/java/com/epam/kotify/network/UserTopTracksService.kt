package com.epam.kotify.network

import com.epam.kotify.model.tracks.TopTracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserTopTracksService {

    @GET("?method=user.gettoptracks&format=json")
    fun getUserTopTracks(
        @Query("user") user: String,
        @Query("limit") limit: String,
        @Query("api_key") apiKey: String
    ): Call<TopTracksResponse>
}