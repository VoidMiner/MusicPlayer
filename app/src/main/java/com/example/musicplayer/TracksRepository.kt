package com.example.musicplayer

// TracksRepository.kt
class TracksRepository(private val apiService: ApiService) {

    suspend fun getTracks(artistName: String, apiKey: String): List<Track> {
        return try {
            apiService.searchTracks(artistName, apiKey)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}



