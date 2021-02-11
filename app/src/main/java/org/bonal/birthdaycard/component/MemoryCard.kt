package org.bonal.birthdaycard.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import org.bonal.birthdaycard.presentation.MemoryCardViewState

@Composable
fun MemoryCard(memory: MemoryCardViewState) {

    val padding = 16.dp

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
        backgroundColor = CardBackgroundColor(memory),
        contentColor = CardForegroundColor(memory),
    ) {
        when (memory) {
            is MemoryCardViewState.TextCardState -> TextCard(memory)
            is MemoryCardViewState.VideoCardState, is MemoryCardViewState.PhotoCardState -> MediaCard(
                memory
            )
        }
    }
}

@Composable
private fun CardBackgroundColor(memory: MemoryCardViewState) =
    if (memory is MemoryCardViewState.TextCardState) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.background
    }

@Composable
private fun CardForegroundColor(memory: MemoryCardViewState) =
    if (memory is MemoryCardViewState.TextCardState) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onBackground
    }

@Composable
fun TextCard(memory: MemoryCardViewState.TextCardState) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (memory.title != null) {
            Text(
                text = memory.title,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
        }
        if (memory.description != null) {
            Text(
                text = memory.description,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MediaCard(memory: MemoryCardViewState) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (memory.title != null) {
            Text(text = memory.title, style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
        }
        if (memory is MemoryCardViewState.PhotoCardState) {
            ImageMemory(memory.pictureUrl)
        } else if (memory is MemoryCardViewState.VideoCardState) {
            VideoMemory(memory)
        }
        if (memory.description != null) {
            Text(memory.description, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
private fun VideoMemory(memory: MemoryCardViewState.VideoCardState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(300.dp)
    ) {
        VideoPlayer(videoUrl = memory.videoUrl, autoPlay = false)
    }
}

@Composable
private fun ImageMemory(imageUrl: String) {
    CoilImage(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxSize(),
        data = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center
    )
}