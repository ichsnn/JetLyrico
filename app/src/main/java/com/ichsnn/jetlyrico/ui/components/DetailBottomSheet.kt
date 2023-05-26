package com.ichsnn.jetlyrico.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ichsnn.jetlyrico.data.model.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(song: Song, modifier: Modifier = Modifier, scaffoldState: BottomSheetScaffoldState, content: @Composable () -> Unit) {    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = modifier.padding(bottom = 8.dp),
                    text = song.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = song.about)
            }
        },
        sheetPeekHeight = 0.dp,
    ) {
        content()
    }
}