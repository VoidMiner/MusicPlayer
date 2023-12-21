// TracksViewModel.kt
package com.example.musicplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TracksViewModel(private val tracksRepository: TracksRepository) : ViewModel() {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> get() = _tracks

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _selectedTrack = MutableLiveData<Track?>()
    val selectedTrack: LiveData<Track?> get() = _selectedTrack

    init {
        fetchTracks("desiredArtistName", "desiredApiKey")
    }

    fun fetchTracks(artistName: String, apiKey: String) {
        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)

                val result = tracksRepository.getTracks(artistName, apiKey)

                setTracks(result)
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }

    fun setTracks(tracks: List<Track>) {
        _tracks.value = tracks
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    private fun setError(error: String?) {
        _error.value = error
    }

    fun onTrackClick(track: Track) {
        _selectedTrack.value = track
    }

    fun onTrackClickComplete() {
        _selectedTrack.value = null
    }
    constructor() : this(TracksRepository(ApiService.create()))
}
