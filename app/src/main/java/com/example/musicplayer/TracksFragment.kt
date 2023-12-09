package com.example.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.FragmentTracksBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TracksFragment : Fragment() {
    private lateinit var binding: FragmentTracksBinding
    private lateinit var viewModel: TracksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTracksBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, TracksViewModelFactory(FavoriteTracksManager())).get(TracksViewModel::class.java)

        // Инициализация RecyclerView
        val adapter = TracksAdapter(emptyList(), viewModel)
        binding.recyclerView.adapter = adapter

        // Загрузка треков из API
        loadTracks()

        return binding.root
    }

    private fun loadTracks() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://freesound.org/apiv2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getTracks()

        call.enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    val tracks = response.body() ?: emptyList()
                    updateTracks(tracks)
                } else {
                    // Обработка ошибки загрузки треков
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                // Обработка ошибки
            }
        })
    }

    private fun updateTracks(newTracks: List<Track>) {
        GlobalScope.launch(Dispatchers.Main) {
            // Обновление списка треков в адаптере
            (binding.recyclerView.adapter as? TracksAdapter)?.updateTracks(newTracks)
        }
    }
}


