package com.epam.kotify.network

import com.epam.kotify.model.artists.TopArtistsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserTopArtistsService {

    @GET("?method=user.gettoptracks&format=json")
    fun getUserTopArtistService(
        @Query("user") user: String,
        @Query("limit") limit: String,
        @Query("api_key") apiKey: String
    ): Call<TopArtistsResponse>
}