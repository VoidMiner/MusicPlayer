package com.example.musicplayer

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val imageUrl: String, // URL изображения обложки трека
    val audioUrl: String // URL аудиофайла трека
)

