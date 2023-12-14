// PlayerFragment.kt
package com.example.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.squareup.picasso.Picasso

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private val playerViewModel: PlayerViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        //TODO КАРАУЛ
        binding.viewModel = playerViewModel

        setupUI()
        observeViewModel()

        binding.shareButton.setOnClickListener {
            // Implement your share functionality here
        }
    }

    private fun setupUI() {
        binding.previousButton.setOnClickListener { playerViewModel.onPreviousClick() }
        binding.playPauseButton.setOnClickListener { playerViewModel.onPlayPauseClick() }
        binding.nextButton.setOnClickListener { playerViewModel.onNextClick() }
        binding.favoriteImageView.setOnClickListener {
            playerViewModel.onAddToFavoritesClick(playerViewModel.currentTrack.value)
        }

        binding.progressLayout.visibility = View.GONE
        binding.progressTimeTextView.text = "0:00"
        binding.totalTimeTextView.text = "0:00"

        playerViewModel.playbackProgress.observe(viewLifecycleOwner, Observer { progress ->
            binding.progressLayout.visibility = View.VISIBLE
            val progressPercentage = (progress * 100 / playerViewModel.currentTrack.value?.intDuration!!).toInt()
            // Update progress bar or time labels as needed
        })
    }

    private fun observeViewModel() {
        playerViewModel.currentTrack.observe(viewLifecycleOwner, Observer { track ->
            track?.let {
                binding.titleTextView.text = it.strTrack
                binding.artistTextView.text = it.strArtist
                binding.totalTimeTextView.text = it.getFormattedDuration()

                Picasso.get().load(it.strTrackThumb).into(binding.albumImageView)
                Picasso.get().load(it.strArtistThumb).into(binding.artistImageView)
            }
        })

        playerViewModel.isPlaying.observe(viewLifecycleOwner, Observer { isPlaying ->
            val playPauseButton = binding.playPauseButton
            playPauseButton.setImageResource(
                if (isPlaying) R.drawable.ic_media_pause else R.drawable.ic_media_play
            )
        })
    }
}

