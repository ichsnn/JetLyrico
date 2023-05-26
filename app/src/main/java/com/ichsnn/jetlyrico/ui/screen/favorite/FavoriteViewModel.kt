package com.ichsnn.jetlyrico.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsnn.jetlyrico.data.SongRepository
import com.ichsnn.jetlyrico.data.model.SongResource
import com.ichsnn.jetlyrico.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val songRepository: SongRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<SongResource>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<SongResource>>> get() = _uiState

    fun getAllFavoriteSong() {
        viewModelScope.launch {
            songRepository.getAllFavorite().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { favoriteSongId ->
                val favoriteSongResources = ArrayList<SongResource>()
                favoriteSongId.forEach {
                    val songResource = songRepository.getSongResourceById(it.id)
                    favoriteSongResources.add(songResource)
                }
                _uiState.value = UiState.Success(favoriteSongResources)
            }
        }
    }
}