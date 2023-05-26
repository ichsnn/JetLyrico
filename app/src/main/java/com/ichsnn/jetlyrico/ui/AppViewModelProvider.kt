package com.ichsnn.jetlyrico.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ichsnn.jetlyrico.JetLyricoApplication
import com.ichsnn.jetlyrico.ui.screen.detail.DetailViewModel
import com.ichsnn.jetlyrico.ui.screen.favorite.FavoriteViewModel
import com.ichsnn.jetlyrico.ui.screen.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                jetLyricoApplication().container.songRepository
            )
        }
        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                jetLyricoApplication().container.songRepository
            )
        }
        initializer {
            FavoriteViewModel(
                jetLyricoApplication().container.songRepository
            )
        }
    }
}

fun CreationExtras.jetLyricoApplication(): JetLyricoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as JetLyricoApplication)