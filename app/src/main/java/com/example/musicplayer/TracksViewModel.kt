package com.example.musicplayer


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TracksViewModel(private val favoriteTracksManager: FavoriteTracksManager) : ViewModel() {

    // Метод для обработки нажатия на кнопку "Добавить в избранное"
    fun onAddToFavoritesClick(track: Track) {
        favoriteTracksManager.addTrackToFavorites(track.id)
        // Здесь вы можете также обновить UI или выполнять другие действия при добавлении в избранное
    }

    // Метод для получения списка избранных треков
    fun getFavoriteTracks(): Set<String> {
        return favoriteTracksManager.getFavoriteTracks()
    }

    // Другие методы и свойства ViewModel, если необходимо
}

class TracksViewModelFactory(private val favoriteTracksManager: FavoriteTracksManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TracksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TracksViewModel(favoriteTracksManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


