package com.ichsnn.jetlyrico.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsnn.jetlyrico.data.SongRepository
import com.ichsnn.jetlyrico.data.model.SongResource
import com.ichsnn.jetlyrico.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: SongRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<SongResource>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<SongResource>>> get() = _uiState

    fun getAllSongResource() {
        viewModelScope.launch {
            repository.getAllSongResource().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { songResources ->
                _uiState.value = UiState.Success(songResources)
            }
        }
    }
}