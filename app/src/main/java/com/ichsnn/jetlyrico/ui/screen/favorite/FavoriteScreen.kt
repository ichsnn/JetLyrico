package com.ichsnn.jetlyrico.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ichsnn.jetlyrico.R
import com.ichsnn.jetlyrico.data.FakeDataSource
import com.ichsnn.jetlyrico.data.model.SongResource
import com.ichsnn.jetlyrico.ui.AppViewModelProvider
import com.ichsnn.jetlyrico.ui.common.UiState
import com.ichsnn.jetlyrico.ui.components.SongCard
import com.ichsnn.jetlyrico.ui.components.SongCardState
import com.ichsnn.jetlyrico.ui.navigation.NavigationDestination
import com.ichsnn.jetlyrico.ui.theme.JetLyricoTheme

object FavoriteDestination : NavigationDestination {
    override val route: String = "favorite"
}

@Composable
fun FavoriteScreen(
    onNavigateUp: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFavoriteSong()
            }

            is UiState.Success -> {
                FavoriteContent(
                    songResources = uiState.data,
                    onNavigateUp = onNavigateUp,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    songResources: List<SongResource>,
    onNavigateUp: () -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    Scaffold(topBar = { FavoriteTopAppBar(onNavigateUp = onNavigateUp) }) { paddingValues ->
        if (songResources.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.you_not_have_favorite_song_yet),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .padding(top = 8.dp)
            ) {
                items(songResources, key = { it.id }) { data ->
                    SongCard(
                        SongCardState(
                            data.id,
                            stringResource(data.title),
                            stringResource(id = data.singer),
                            data.imgPhoto
                        ),
                        onItemClick = navigateToDetail
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopAppBar(modifier: Modifier = Modifier, onNavigateUp: () -> Unit) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.my_favorite)) },
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.navigation_back)
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FavoriteContentPreview() {
    JetLyricoTheme {
        FavoriteContent(
            songResources = FakeDataSource.dummySongResources,
            navigateToDetail = {},
            onNavigateUp = {})
    }
}