package com.epam.kotify.api

import androidx.lifecycle.LiveData
import com.epam.kotify.model.albums.TopAlbumsResponse
import com.epam.kotify.model.artists.TopArtistsResponse
import com.epam.kotify.model.tracks.TopTracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryTopService {

//    @GET("?method=geo.gettopartists&format=json")
//    fun getGeoTopArtists(
//        @Query("country") country: String,
//        @Query("limit") limit: Int,
//        @Query("api_key") apiKey: String
//    ): Call<TopArtistsResponse>

    @GET("?method=geo.gettopartists&format=json")
    fun getGeoTopArtists(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponse<TopArtistsResponse>>

    @GET("?method=user.gettopalbums&format=json")
    fun getUserTopAlbums(
        @Query("user") user: String,
        @Query("limit") limit: String,
        @Query("api_key") apiKey: String
    ): Call<TopAlbumsResponse>

//    @GET("?method=geo.gettoptracks&format=json")
//    fun getGeoTopTracks(
//        @Query("country") country: String,
//        @Query("limit") limit: Int,
//        @Query("api_key") apiKey: String
//    ): Call<TopTracksResponse>
//
    @GET("?method=geo.gettoptracks&format=json")
    fun getGeoTopTracks(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponse<TopTracksResponse>>
}