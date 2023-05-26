package com.ichsnn.jetlyrico.data

import android.content.Context
import com.ichsnn.jetlyrico.data.room.JetLyricoDatabase

interface AppContainer {
    val songRepository: SongRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val songRepository: SongRepository by lazy {
        SongRepository(JetLyricoDatabase.getDatabase(context).favoriteSongDao())
    }
}