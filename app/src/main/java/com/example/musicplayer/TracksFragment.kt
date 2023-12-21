// TracksFragment.kt
package com.example.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.FragmentTracksBinding
import kotlinx.coroutines.launch

class TracksFragment : Fragment() {

    private lateinit var binding: FragmentTracksBinding
    private val viewModel: TracksViewModel by activityViewModels()

    private val tracksAdapter: TracksAdapter by lazy {
        TracksAdapter { track -> viewModel.onTrackClick(track) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchTracks()
        binding.loadingProgressBar.visibility = View.GONE

    }

    private fun setupRecyclerView() {
        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksRecyclerView.adapter = tracksAdapter
    }

    private fun fetchTracks() {
        val apiKey = "2"
        val artistName = "coldplay"

        viewModel.setLoading(true)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val tracks = ApiService.create().searchTracks(apiKey, artistName)
                viewModel.setTracks(tracks)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                viewModel.setLoading(false)
            }
        }
    }
}
