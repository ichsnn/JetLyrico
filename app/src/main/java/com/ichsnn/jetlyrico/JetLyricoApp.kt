package com.ichsnn.jetlyrico

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ichsnn.jetlyrico.ui.navigation.JetLyricoNavHost

@Composable
fun JetLyricoApp(navController: NavHostController = rememberNavController()) {
    JetLyricoNavHost(navController = navController)
}