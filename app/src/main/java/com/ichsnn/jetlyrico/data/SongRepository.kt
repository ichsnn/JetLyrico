package com.ichsnn.jetlyrico.data

import com.ichsnn.jetlyrico.data.enitity.FavoriteSongEntity
import com.ichsnn.jetlyrico.data.model.SongResource
import com.ichsnn.jetlyrico.data.room.FavoriteSongDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SongRepository(private val favoriteSongDao: FavoriteSongDao) {
    private val songResource = mutableListOf<SongResource>()

    init {
        if (songResource.isEmpty()) {
            songResource.addAll(FakeDataSource.dummySongResources)
        }
    }

    fun getAllSongResource(): Flow<List<SongResource>> {
        return flowOf(songResource)
    }

    fun getSongResourceById(id: Int): SongResource {
        return songResource.first {
            it.id == id
        }
    }

    fun getAllFavorite(): Flow<List<FavoriteSongEntity>> = favoriteSongDao.getFavoriteSong()

    suspend fun addFavorite(favoriteSong: FavoriteSongEntity) {
        favoriteSongDao.insert(favoriteSong)
    }

    suspend fun deleteFavorite(favoriteSong: FavoriteSongEntity) {
        favoriteSongDao.delete(favoriteSong)
    }

    fun isSongFavorite(id: Int): Flow<Boolean> = favoriteSongDao.isFavorite(id)
}