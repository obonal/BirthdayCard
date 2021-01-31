package org.bonal.birthdaycard.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage
import org.bonal.birthdaycard.MessageSender
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.model.BirthdayData
import org.bonal.birthdaycard.model.defaultBirthdayData
import org.bonal.birthdaycard.ui.theme.BirthdayCardTheme

@Composable
fun HomePage(
    birthdayData: BirthdayData,
    messageSender: MessageSender
) {
    BirthdayCardTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    val title = stringResource(id = R.string.app_name)
                    TopAppBar(
                        title = { Text(text = title) }
                    )
                },
                bodyContent = {
                    BirthdayFeed(
                        birthdayData = birthdayData,
                        messageSender = messageSender
                    )
                }
            )

        }
    }
}

@Composable
private fun BirthdayFeed(
    birthdayData: BirthdayData,
    messageSender: MessageSender
) {
    rememberScrollState(0f)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // use `item` for separate elements like headers
        // and `items` for lists of identical elements
        item {
            BirthdayCardHeader(birthdayData)
            birthdayData.guestList.forEach {
                BirthdayGuestCard(it, messageSender)
            }
        }
    }
}

@Composable
private fun BirthdayCardHeader(birthdayData: BirthdayData) {
    val padding = 16.dp
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp), elevation = 8.dp,
        backgroundColor = colorResource(id = R.color.purple_200),
        contentColor = colorResource(id = R.color.white)
    ) {
        Column(
            Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageModifier = Modifier
                .padding(end = 4.dp)
                .preferredSize(100.dp, 100.dp)
                .clip(CircleShape)
            val birthdayHost = birthdayData.birthdayHost
            birthdayHost.pictureUrl?.let { pictureUrl ->
                GlideImage(
                    data = pictureUrl,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(Modifier.fillMaxSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    error = {
                        PlaceHolderImage(imageModifier)
                    }
                )
            } ?: PlaceHolderImage(imageModifier)

            Text(
                stringResource(R.string.birthday_card_title, birthdayHost.name),
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
            Spacer(Modifier.preferredSize(padding))
            Text(
                birthdayData.birthdayCardMessage,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(
        birthdayData = defaultBirthdayData,
        messageSender = object : MessageSender {
            override fun sendMessage(phoneNumber: String?, message: String?) {
            }
        }
    )
}