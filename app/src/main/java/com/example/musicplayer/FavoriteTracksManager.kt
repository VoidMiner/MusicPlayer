// FavoriteTracksManager.kt
package com.example.musicplayer

import android.content.Context
import com.google.gson.Gson

class FavoriteTracksManager(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("favorite_tracks", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getFavoriteTracks(): List<Track> {
        val json = sharedPreferences.getString("favorite_tracks", null)
        return if (json != null) {
            gson.fromJson(json, Array<Track>::class.java).toList()
        } else {
            emptyList()
        }
    }

    fun addFavoriteTrack(track: Track) {
        val currentFavorites = getFavoriteTracks().toMutableList()
        currentFavorites.add(track)
        saveFavorites(currentFavorites)
    }

    fun removeFavoriteTrack(track: Track) {
        val currentFavorites = getFavoriteTracks().toMutableList()
        currentFavorites.remove(track)
        saveFavorites(currentFavorites)
    }

    private fun saveFavorites(favorites: List<Track>) {
        val json = gson.toJson(favorites.toTypedArray())
        sharedPreferences.edit().putString("favorite_tracks", json).apply()
    }
}
