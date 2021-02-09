package org.bonal.birthdaycard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.accompanist.coil.CoilImage
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.model.BirthdayHost
import org.bonal.birthdaycard.ui.theme.BirthdayCardTheme
import org.bonal.birthdaycard.viewmodel.BirthdayGuestCardViewState
import org.bonal.birthdaycard.viewmodel.BirthdayViewModel

@Composable
fun HomePage(viewModel: BirthdayViewModel, navigateToMemories: () -> Unit) {
    val birthdayHost: BirthdayHost? by viewModel.birthdayHost.observeAsState()
    BirthdayCardTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    val title = birthdayHost?.name?.let {
                        stringResource(id = R.string.birthday_card_title, it)
                    } ?: stringResource(id = R.string.toolbar_title)
                    TopAppBar(
                        title = { Text(text = title) }
                    )
                },
                bodyContent = {
                    BirthdayFeed(
                        viewModel = viewModel,
                        navigateToMemories = navigateToMemories
                    )
                }
            )

        }
    }
}

@Composable
private fun BirthdayFeed(
    viewModel: BirthdayViewModel,
    navigateToMemories: () -> Unit
) {
    val birthdayHost: BirthdayHost? by viewModel.birthdayHost.observeAsState()
    val guestList: List<BirthdayGuestCardViewState> by viewModel.guestList.observeAsState(emptyList())
    val birthdayCardMessage: String? by viewModel.birthdayCardMessage.observeAsState()
    val galleryButtonLabel: String? by viewModel.galleryButtonLabel.observeAsState()
    val birthdayCardBackground: String? by viewModel.birthdayCardBackground.observeAsState()

    rememberScrollState(0f)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            BirthdayCardHeader(
                birthdayHost = birthdayHost,
                message = birthdayCardMessage,
                backgroundImage = birthdayCardBackground,
                navigateToMemories = navigateToMemories,
                galleryButtonLabel = galleryButtonLabel
            )
        }
        items(guestList.size) { index ->
            BirthdayGuestCard(guestList[index])
        }
    }
}

@Composable
private fun BirthdayCardHeader(
    birthdayHost: BirthdayHost?,
    message: String?,
    backgroundImage: String?,
    navigateToMemories: () -> Unit,
    galleryButtonLabel: String?
) {
    Card(
        Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        elevation = 8.dp,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Box(
            modifier = Modifier.padding(8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            CardBackgroundImage(backgroundImage)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(semiTransparentBackground()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    HostSection(birthdayHost)
                    BirthdayHeaderMessage(message)
                    MemoriesButton(
                        buttonLabel = galleryButtonLabel,
                        navigateToMemories = navigateToMemories
                    )
                }
            }
        }
    }
}

@Composable
private fun BirthdayHeaderMessage(message: String?) {
    message?.let {
        Text(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
            text = message,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun MemoriesButton(buttonLabel: String?, navigateToMemories: () -> Unit) {
    if (buttonLabel.isNullOrBlank()) return
    val buttonModifier = Modifier
        .wrapContentWidth()
//        .background(MaterialTheme.colors.background)
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp, top = 24.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            modifier = buttonModifier,
            onClick = navigateToMemories
        ) {
            Text(
                text = buttonLabel,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
private fun CardBackgroundImage(backgroundImage: String?) {
    if (backgroundImage.isNullOrBlank()) return
    CoilImage(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        data = backgroundImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter
    )
}

@Composable
private fun HostSection(birthdayHost: BirthdayHost?) {
    birthdayHost?.let { host ->
        val age = host.age
        val rowModifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
        Row(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (age % 10 == 0) {
                val agePrefix = (age / 10).toString()
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp, top = 0.dp),
                    text = agePrefix,
                    fontSize = 120.sp
                )
            }
            HostHeroImage(birthdayHost = host)
        }
    }
}

@Composable
private fun HostHeroImage(birthdayHost: BirthdayHost) {
    val imageModifier = Modifier
        .preferredSize(100.dp, 100.dp)
        .clip(CircleShape)
    birthdayHost.pictureUrl?.let {
        CoilImage(
            data = it,
            modifier = imageModifier,
            contentDescription = birthdayHost.name,
            contentScale = ContentScale.Crop,
            loading = {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            error = {
                PlaceHolderImage(
                    modifier = imageModifier,
                    tintColor = MaterialTheme.colors.onPrimary
                )
            }
        )
    } ?: PlaceHolderImage(imageModifier, tintColor = MaterialTheme.colors.onPrimary)
}

@Composable
private fun semiTransparentBackground(): Color =
    MaterialTheme.colors.primary.copy(alpha = 0.3f)

@Preview
@Composable
fun HostSectionPreview() {
    HostSection(
        birthdayHost = BirthdayHost(name = "Bob", age = 70)
    )
}