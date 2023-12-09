package com.example.musicplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicplayer.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private val favoriteTracksManager = FavoriteTracksManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)

        // Инициализация элементов управления проигрывателем
        // ...

        // Обработка нажатия на кнопку "Добавить в избранное"
        binding.addToFavoriteButton.setOnClickListener {
            val selectedTrack = // Получение текущего трека
                favoriteTracksManager.addTrackToFavorites(selectedTrack)
        }

        return binding.root
    }
}
