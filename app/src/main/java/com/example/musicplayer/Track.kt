// Track.kt
package com.example.musicplayer

data class Track(
    val idTrack: String,
    val strTrack: String,
    val strArtist: String,
    val intDuration: Int,
    val strTrackThumb: String, // URL изображения для трека
    val strArtistThumb: String // URL изображения для артиста
) {
    fun getFormattedDuration(): String {
        // Implement formatting of duration (e.g., convert seconds to minutes:seconds)
        return "0:00"
    }
}

