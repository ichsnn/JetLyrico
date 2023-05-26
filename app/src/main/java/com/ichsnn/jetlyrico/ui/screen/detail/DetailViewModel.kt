package com.ichsnn.jetlyrico.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsnn.jetlyrico.data.SongRepository
import com.ichsnn.jetlyrico.data.enitity.FavoriteSongEntity
import com.ichsnn.jetlyrico.data.model.SongResource
import com.ichsnn.jetlyrico.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(savedStateHandle: SavedStateHandle, private val repository: SongRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<SongResource>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<SongResource>> get() = _uiState

    private val itemId: Int = checkNotNull(savedStateHandle[DetailDestination.songIdArg])

    fun getSongResourceDetail() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getSongResourceById(itemId))
        }
    }

    fun addFavorite(id: Int) {
        viewModelScope.launch {
            repository.addFavorite(FavoriteSongEntity(id))
        }
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            repository.deleteFavorite(FavoriteSongEntity(id))
        }
    }

    fun isFavorite(id: Int) = repository.isSongFavorite(id)
}