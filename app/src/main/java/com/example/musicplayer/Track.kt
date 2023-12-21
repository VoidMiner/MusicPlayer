// Track.kt
package com.example.musicplayer

data class Track(
    val idTrack: String,
    val strTrack: String,
    val strArtist: String,
    val intDuration: Int,
    val strTrackThumb: String,
    val strArtistThumb: String
) {
    fun getFormattedDuration(): String {
        val minutes = intDuration / 60
        val seconds = intDuration % 60
        return "$minutes:${String.format("%02d", seconds)}"
    }
}


