package org.bonal.birthdaycard.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import org.bonal.birthdaycard.model.BirthdayMemory
import org.bonal.birthdaycard.viewmodel.MemoryCardViewState

@Composable
fun MemoryCard(memory: MemoryCardViewState) {

    val padding = 16.dp

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
        ) {
            val imageModifier = Modifier
                .padding(end = 8.dp)
                .preferredSize(75.dp, 75.dp)
                .clip(CircleShape)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = memory.title, style = MaterialTheme.typography.h5)
                if (memory is MemoryCardViewState.PhotoCardState) {
                    ImageMemory(memory.pictureUrl)
                } else if (memory is MemoryCardViewState.VideoCardState) {
                    VideoPlayer(videoUrl = memory.videoUrl, autoPlay = false)
                }
                val description = memory.description
                if (description != null) {
                    Text(description, style = MaterialTheme.typography.body1)
                }
            }
        }
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