package com.epam.kotify.network

import com.epam.kotify.model.albums.TopAlbumsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserTopAlbumsService {

    @GET("?method=user.gettopalbums&format=json")
    fun getUserTopAlbums(
        @Query("user") user: String,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): Call<TopAlbumsResponse>
}