// ApiService.kt
package com.example.musicplayer

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit


interface ApiService {

    @GET("searchtrack.php")
    suspend fun searchTracks(
        @Query("s") artistName: String,
        @Query("t") trackName: String
    ): List<Track>

    companion object {
        fun create(): ApiService {
            return RetrofitClient.retrofit.create(ApiService::class.java)
        }
    }
}
