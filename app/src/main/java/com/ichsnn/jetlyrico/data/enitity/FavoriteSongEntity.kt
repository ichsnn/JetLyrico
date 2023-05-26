package com.ichsnn.jetlyrico.data.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_song")
data class FavoriteSongEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int
)