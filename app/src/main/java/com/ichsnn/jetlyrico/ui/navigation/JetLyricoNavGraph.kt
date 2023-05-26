package com.ichsnn.jetlyrico.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ichsnn.jetlyrico.ui.screen.about.AboutDestination
import com.ichsnn.jetlyrico.ui.screen.about.AboutScreen
import com.ichsnn.jetlyrico.ui.screen.detail.DetailDestination
import com.ichsnn.jetlyrico.ui.screen.detail.DetailScreen
import com.ichsnn.jetlyrico.ui.screen.favorite.FavoriteDestination
import com.ichsnn.jetlyrico.ui.screen.favorite.FavoriteScreen
import com.ichsnn.jetlyrico.ui.screen.home.HomeDestination
import com.ichsnn.jetlyrico.ui.screen.home.HomeScreen

@Composable
fun JetLyricoNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToDetail = { navController.navigate("${DetailDestination.route}/$it") },
                navigateToAboutMe = { navController.navigate(AboutDestination.route) },
                navigateToFavorite = { navController.navigate(FavoriteDestination.route) })
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {
            val context = LocalContext.current
            DetailScreen(
                onNavigateUp = { navController.navigateUp() },
                onShareClick = { lyrics -> shareLyrics(context = context, lyrics) })
        }
        composable(route = AboutDestination.route) {
            AboutScreen(onNavigateUp = { navController.navigateUp() })
        }
        composable(route = FavoriteDestination.route) {
            FavoriteScreen(
                navigateToDetail = { navController.navigate("${DetailDestination.route}/$it") },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}

private fun shareLyrics(context: Context, lyrics: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, lyrics)
    }
    context.startActivity(Intent.createChooser(intent, "Share Lyrics"))
}