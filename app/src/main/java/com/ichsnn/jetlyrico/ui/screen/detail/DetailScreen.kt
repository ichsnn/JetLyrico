package com.ichsnn.jetlyrico.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ichsnn.jetlyrico.R
import com.ichsnn.jetlyrico.data.FakeDataSource
import com.ichsnn.jetlyrico.data.model.Song
import com.ichsnn.jetlyrico.ui.AppViewModelProvider
import com.ichsnn.jetlyrico.ui.common.UiState
import com.ichsnn.jetlyrico.ui.components.DetailBottomSheet
import com.ichsnn.jetlyrico.ui.components.SongDetailHeader
import com.ichsnn.jetlyrico.ui.navigation.NavigationDestination
import com.ichsnn.jetlyrico.ui.theme.JetLyricoTheme
import kotlinx.coroutines.launch

object DetailDestination : NavigationDestination {
    override val route = "detail"
    const val songIdArg = "songId"
    val routeWithArgs = "$route/{$songIdArg}"

}

@Composable
fun DetailScreen(
    onNavigateUp: () -> Unit, modifier: Modifier = Modifier,
    onShareClick: (String) -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> viewModel.getSongResourceDetail()
            is UiState.Success -> {
                val songResource = uiState.data
                val song = Song(
                    id = songResource.id,
                    title = stringResource(id = songResource.title),
                    singer = stringResource(
                        id = songResource.singer
                    ),
                    lyrics = stringResource(id = songResource.lyrics),
                    imgPhoto = songResource.imgPhoto,
                    releaseDate = stringResource(id = songResource.releaseDate),
                    about = stringResource(id = songResource.about)
                )
                val isFavorite by viewModel.isFavorite(song.id).collectAsState(initial = false)
                DetailContent(
                    song = song,
                    isFavorite = isFavorite,
                    onFavoriteClick = {
                        if (isFavorite) viewModel.deleteFavorite(song.id) else viewModel.addFavorite(
                            song.id
                        )
                    },
                    onNavigateUp = onNavigateUp,
                    onShareClick = onShareClick,
                    modifier = modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    song: Song,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    DetailBottomSheet(song = song, scaffoldState = scaffoldState) {
        Scaffold(topBar = {
            DetailTopAppBar(
                isFavorite = isFavorite,
                onFavoriteClick = onFavoriteClick,
                onNavigateUp = onNavigateUp,
                onShareClick = {
                    onShareClick(song.lyrics)
                }
            )
        }) { paddingValues ->
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                SongDetailHeader(song = song, onAboutClick = {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                })
                Text(
                    modifier = modifier.padding(16.dp),
                    text = song.lyrics,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean = false,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(
                onClick = { onNavigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, null)
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(id = R.string.menu_share)
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite)
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DetailTopAppBarPreview() {
    DetailTopAppBar(
        onNavigateUp = {},
        onShareClick = {},
        onFavoriteClick = {})
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DetailContentPreview() {
    JetLyricoTheme {
        DetailContent(song = Song(
            FakeDataSource.dummySongResources[1].id,
            stringResource(id = FakeDataSource.dummySongResources[1].title),
            stringResource(id = FakeDataSource.dummySongResources[1].singer),
            stringResource(id = FakeDataSource.dummySongResources[1].lyrics),
            FakeDataSource.dummySongResources[1].imgPhoto,
            stringResource(id = FakeDataSource.dummySongResources[1].releaseDate),
            stringResource(id = FakeDataSource.dummySongResources[1].about)
        ), false, {}, {}, {})
    }
}