// PlayerViewModel.kt
package com.example.musicplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import android.content.Context


class PlayerViewModel(context: Context) : ViewModel() {

    val currentTrack = MutableLiveData<Track?>()
    val isPlaying = MutableLiveData<Boolean>()
    val playbackProgress = MutableLiveData<Long>()

    private val favoriteTracksManager = FavoriteTracksManager(context)


    fun onPreviousClick() {
        // Обработка нажатия на кнопку "Предыдущий трек"
        // Ваш код здесь
    }

    fun onPlayPauseClick() {
        // Обработка нажатия на кнопку "Воспроизвести/Пауза"
        isPlaying.value = isPlaying.value?.not() ?: false
    }

    fun onNextClick() {
        // Обработка нажатия на кнопку "Следующий трек"
        // Ваш код здесь
    }

    fun onAddToFavoritesClick(track: Track?) {
        // Обработка нажатия на кнопку "Добавить/Удалить из избранного"
        track?.let {
            if (favoriteTracksManager.isFavorite(track)) {
                favoriteTracksManager.removeFavoriteTrack(it)
            } else {
                favoriteTracksManager.addFavoriteTrack(it)
            }
        }
    }

    init {
        // Виртуальное воспроизведение прогресса (для тестирования)
        viewModelScope.launch {
            flow {
                repeat(100) {
                    emit(it.toLong())
                    delay(1000)
                }
            }.collect {
                playbackProgress.postValue(it)
            }
        }
    }
}
