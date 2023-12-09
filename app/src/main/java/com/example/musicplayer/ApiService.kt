package com.example.musicplayer

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("search/text/?query=music&format=json")
    fun getTracks(): Call<List<Track>>
}
