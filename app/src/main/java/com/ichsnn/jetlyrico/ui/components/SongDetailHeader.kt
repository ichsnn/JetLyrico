package com.ichsnn.jetlyrico.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ichsnn.jetlyrico.data.FakeDataSource
import com.ichsnn.jetlyrico.data.model.Song

@Composable
fun SongDetailHeader(
    song: Song,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .aspectRatio(1f)
                .zIndex(2f),
        )
        Image(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .zIndex(1f),
            painter = painterResource(id = song.imgPhoto),
            contentDescription = null
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .zIndex(3f)
                .align(Alignment.BottomStart)
                .padding(vertical = 8.dp),
        ) {
            Column(modifier.padding(vertical = 8.dp)) {
                Text(
                    modifier = modifier.padding(horizontal = 16.dp),
                    text = song.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )
                Text(
                    modifier = modifier.padding(horizontal = 16.dp),
                    text = song.singer,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
            Divider()
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onAboutClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier.weight(1f),
                    text = song.about,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { onAboutClick() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Divider()
            Column(modifier.padding(vertical = 8.dp)) {
                Text(
                    text = song.releaseDate,
                    modifier = modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongDetailHeader() {
    SongDetailHeader(
        song = Song(
            FakeDataSource.dummySongResources[1].id,
            stringResource(id = FakeDataSource.dummySongResources[1].title),
            stringResource(id = FakeDataSource.dummySongResources[1].singer),
            stringResource(id = FakeDataSource.dummySongResources[1].lyrics),
            FakeDataSource.dummySongResources[1].imgPhoto,
            stringResource(id = FakeDataSource.dummySongResources[1].releaseDate),
            stringResource(id = FakeDataSource.dummySongResources[1].about)
        ), onAboutClick = {})
}