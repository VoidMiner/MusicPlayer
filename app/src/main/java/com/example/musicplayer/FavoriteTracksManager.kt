package com.example.musicplayer

import android.content.Context
import android.content.SharedPreferences


class FavoriteTracksManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("favorite_tracks", Context.MODE_PRIVATE)
    }
    private val favoriteTracks = mutableSetOf<String>()

    fun getFavoriteTracks(): Set<String> {
        return favoriteTracks
    }

    fun addTrackToFavorites(trackId: String) {
        favoriteTracks.add(trackId)
        saveFavoriteTracks()
    }

    fun removeTrackFromFavorites(trackId: String) {
        favoriteTracks.remove(trackId)
        saveFavoriteTracks()
    }

    private fun saveFavoriteTracks() {
        sharedPreferences.edit().putStringSet("favorite_tracks", favoriteTracks).apply()
    }
}
