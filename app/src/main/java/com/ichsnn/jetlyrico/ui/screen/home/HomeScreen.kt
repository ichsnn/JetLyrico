package com.ichsnn.jetlyrico.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

object HomeDestination : NavigationDestination {
    override val route = "home"

}

@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    navigateToAboutMe: () -> Unit,
    navigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllSongResource()
            }

            is UiState.Error -> {}
            is UiState.Success -> {
                HomeContent(
                    songResources = uiState.data,
                    navigateToDetail = navigateToDetail,
                    navigateToAboutMe = navigateToAboutMe,
                    navigateToFavorite = navigateToFavorite,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    songResources: List<SongResource>,
    navigateToDetail: (Int) -> Unit,
    navigateToAboutMe: () -> Unit,
    navigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { HomeTopAppBar(navigateToAboutMe = navigateToAboutMe) },
        floatingActionButton = { FavFAB(navigateToFavorite = navigateToFavorite) }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(top = 8.dp),
        ) {
            items(songResources, key = {it.id}) { data ->
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

@Composable
fun FavFAB(modifier: Modifier = Modifier, navigateToFavorite: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = navigateToFavorite,
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = stringResource(R.string.favorite_page)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    JetLyricoTheme {
        HomeContent(songResources = FakeDataSource.dummySongResources, {}, {}, {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(navigateToAboutMe: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        modifier = modifier,
        actions = {
            IconButton(onClick = navigateToAboutMe) {
                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = stringResource(R.string.about_page)
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeTopAppBarPreview() {
    HomeTopAppBar({})
}