package com.ichsnn.jetlyrico.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ichsnn.jetlyrico.data.enitity.FavoriteSongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteSongDao {
    @Query("SELECT * FROM favorite_song")
    fun getFavoriteSong(): Flow<List<FavoriteSongEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteSong: FavoriteSongEntity)

    @Delete
    suspend fun delete(favoriteSong: FavoriteSongEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_song WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>
}