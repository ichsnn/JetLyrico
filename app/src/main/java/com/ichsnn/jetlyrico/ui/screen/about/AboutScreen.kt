package com.ichsnn.jetlyrico.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ichsnn.jetlyrico.R
import com.ichsnn.jetlyrico.ui.navigation.NavigationDestination
import com.ichsnn.jetlyrico.ui.theme.JetLyricoTheme

object AboutDestination : NavigationDestination {
    override val route: String = "about_me"
}

@Composable
fun AboutScreen(onNavigateUp: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(topBar = { AboutTopAppBar(onNavigateUp = onNavigateUp) }) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier
                    .size(200.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.me),
                contentDescription = stringResource(R.string.my_photo),
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.about_name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = stringResource(id = R.string.about_email),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.tv_about_me_desc),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopAppBar(onNavigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.about_me)) },
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigation_back)
                )
            }
        })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    JetLyricoTheme {
        AboutScreen({})
    }
}