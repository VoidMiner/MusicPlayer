package com.example.musicplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemTrackBinding
import com.squareup.picasso.Picasso

class TracksAdapter(private val onTrackClick: (Track) -> Unit) :
    ListAdapter<Track, TracksAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onTrackClick)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }

    class TrackViewHolder(
        private val binding: ItemTrackBinding,
        private val onTrackClick: (Track) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            binding.apply {
                titleTextView.text = track.strTrack
                artistTextView.text = track.strArtist

                // Здесь загружаем изображения с использованием Picasso
                Picasso.get().load(track.strTrackThumb).into(trackImageView)
                Picasso.get().load(track.strArtistThumb).into(artistImageView)
                // Здесь также можете загружать изображение альбома (если есть) с использованием Picasso

                root.setOnClickListener { onTrackClick(track) }
            }
        }
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.idTrack == newItem.idTrack
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}


