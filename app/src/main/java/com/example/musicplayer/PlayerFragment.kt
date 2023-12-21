// PlayerFragment.kt
package com.example.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.squareup.picasso.Picasso

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()

        binding.shareButton.setOnClickListener {
            // Обработка нажатия на кнопку "Поделиться"
            // Ваш код здесь
        }
    }

    private fun setupUI() {
        binding.previousButton.setOnClickListener { viewModel.onPreviousClick() }
        binding.playPauseButton.setOnClickListener { viewModel.onPlayPauseClick() }
        binding.nextButton.setOnClickListener { viewModel.onNextClick() }
        binding.favoriteImageView.setOnClickListener {
            viewModel.onAddToFavoritesClick(viewModel.currentTrack.value)
        }

        binding.progressLayout.visibility = View.GONE
        binding.progressTimeTextView.text = "0:00"
        binding.totalTimeTextView.text = "0:00"

        viewModel.playbackProgress.observe(viewLifecycleOwner, Observer { progress ->
            binding.progressLayout.visibility = View.VISIBLE
            val progressPercentage =
                (progress * 100 / viewModel.currentTrack.value?.intDuration!!).toInt()
            // Обновление полосы прогресса или меток времени по мере необходимости
        })
    }

    private fun observeViewModel() {
        viewModel.currentTrack.observe(viewLifecycleOwner, Observer { track ->
            track?.let {
                binding.titleTextView.text = it.strTrack
                binding.artistTextView.text = it.strArtist
                binding.totalTimeTextView.text = it.getFormattedDuration()

                Picasso.get().load(it.strTrackThumb).into(binding.albumImageView)
                Picasso.get().load(it.strArtistThumb).into(binding.artistImageView)
            }
        })

        viewModel.isPlaying.observe(viewLifecycleOwner, Observer { isPlaying ->
            val playPauseButton = binding.playPauseButton
            playPauseButton.setImageResource(
                if (isPlaying) R.drawable.ic_media_pause else R.drawable.ic_media_play
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
