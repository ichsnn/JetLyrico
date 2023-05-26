package com.ichsnn.jetlyrico.data.model

data class Song(
    val id: Int,
    val title: String,
    val singer: String,
    val lyrics: String,
    val imgPhoto: Int,
    val releaseDate: String,
    val about: String,
)