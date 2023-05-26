package com.ichsnn.jetlyrico.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ichsnn.jetlyrico.data.enitity.FavoriteSongEntity

@Database(entities = [FavoriteSongEntity::class], version = 1, exportSchema = false)
abstract class JetLyricoDatabase : RoomDatabase() {
    abstract fun favoriteSongDao(): FavoriteSongDao

    companion object {
        @Volatile
        private var Instance: JetLyricoDatabase? = null

        fun getDatabase(context: Context): JetLyricoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, JetLyricoDatabase::class.java, "jetlyrico_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}