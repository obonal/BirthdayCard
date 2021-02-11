package org.bonal.birthdaycard.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.presentation.BirthdayGuestCardViewState

@Composable
fun BirthdayGuestCard(
    cardViewState: BirthdayGuestCardViewState,
) {

    // expanded is "internal state" for BirthdayGuestCard
    val expandedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val padding = 16.dp

    Card(
        Modifier
            .preferredWidthIn(300.dp, 600.dp)
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
            Row(verticalAlignment = Alignment.Top) {
                cardViewState.pictureUrl?.let {
                    CoilImage(
                        data = cardViewState.pictureUrl,
                        contentDescription = cardViewState.name,
                        modifier = imageModifier,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(Modifier.fillMaxSize()) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        },
                        error = {
                            PlaceHolderImage(
                                modifier = imageModifier,
                                vectorIconRes = R.drawable.ic_guest,
                                contentDescription = cardViewState.name
                            )
                        }
                    )
                } ?: PlaceHolderImage(imageModifier, R.drawable.ic_guest)
                Column {
                    Text(cardViewState.name, style = MaterialTheme.typography.h6)
                    val message = cardViewState.message
                    if (message != null) {
                        Text(message, style = MaterialTheme.typography.body1)
                    }
                }
            }
            if (expandedState.value) {
                Box(modifier = Modifier.fillMaxWidth().preferredHeight(300.dp)) {
                    VideoPlayer(videoUrl = cardViewState.video!!, autoPlay = true)
                }
            }
            if (cardViewState.hasActions()) {
                ButtonRow(padding, cardViewState, expandedState)
            }
        }
    }
}

@Composable
private fun ButtonRow(
    padding: Dp,
    cardViewState: BirthdayGuestCardViewState,
    expandedState: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val buttonModifier = Modifier
            .weight(1f)
            .padding(2.dp)
        if (cardViewState.video != null) {
            val videoButtonLabel = if (expandedState.value) {
                stringResource(R.string.stop_video_button_label)
            } else {
                stringResource(R.string.play_video_button_label)
            }
            Button(modifier = buttonModifier,
                onClick = { expandedState.value = !expandedState.value }) {
                Text(
                    videoButtonLabel,
                    style = MaterialTheme.typography.button
                )
            }
        }
        val secondaryAction = cardViewState.secondaryAction
        if (secondaryAction != null) {
            Button(modifier = buttonModifier,
                onClick = { secondaryAction.performAction() }) {
                Text(
                    text = secondaryAction.actionLabel,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuestCardPreview() {
    BirthdayGuestCard(
        cardViewState = BirthdayGuestCardViewState(
            name = "Birthday Boy",
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            video = "an non empty video url",
            secondaryAction = GuestCardAction("Another action") {}
        )
    )
}

class GuestCardAction(
    val actionLabel: String,
    val performAction: () -> Unit
)