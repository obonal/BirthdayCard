package org.bonal.birthdaycard.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.model.BirthdayGuest

@Composable
fun BirthdayGuestCard(
    guest: BirthdayGuest,
    secondaryAction: GuestCardAction? = null
) {

    // expanded is "internal state" for BirthdayGuestCard
    val expandedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val padding = 16.dp

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
        backgroundColor = colorResource(id = R.color.purple_200),
        contentColor = colorResource(id = R.color.white)
    ) {
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
        ) {
            val imageModifier = Modifier
                .padding(end = 8.dp)
                .preferredSize(50.dp, 50.dp)
                .clip(MaterialTheme.shapes.medium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                guest.pictureUrl?.let {
                    GlideImage(
                        data = guest.pictureUrl,
                        modifier = imageModifier,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(Modifier.fillMaxSize()) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        },
                        error = {
                            PlaceHolderImage(imageModifier, R.drawable.ic_guest)
                        }
                    )
                } ?: PlaceHolderImage(imageModifier, R.drawable.ic_guest)
                Column {
                    Text(guest.name, style = MaterialTheme.typography.h6)
                    Text(guest.message, style = MaterialTheme.typography.body1)
                }
            }
            if (guest.hasActions()) {
                ButtonRow(padding, guest, secondaryAction, expandedState)
            }
            if (expandedState.value) {
                VideoPlayer(videoUrl = guest.video!!)
            }
        }
    }
}

@Composable
private fun ButtonRow(
    padding: Dp,
    guest: BirthdayGuest,
    secondaryAction: GuestCardAction?,
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
        if (guest.video != null) {
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
        if (secondaryAction != null) {
            Button(modifier = buttonModifier,
                onClick = { secondaryAction.performAction(guest) }) {
                Text(
                    text = secondaryAction.actionLabel,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

private fun BirthdayGuest.hasActions(): Boolean = this.video != null || this.phoneNumber != null

@Preview(showBackground = true)
@Composable
fun GuestCardPreview() {
    BirthdayGuestCard(
        guest = BirthdayGuest(
            name = "Birthday Boy",
            pictureUrl = "asset:///olivier.jpg",
            video = "an non empty video url"
        ),
        secondaryAction = GuestCardAction("Message") {}
    )
}

class GuestCardAction(
    val actionLabel: String,
    val performAction: (BirthdayGuest) -> Unit
)