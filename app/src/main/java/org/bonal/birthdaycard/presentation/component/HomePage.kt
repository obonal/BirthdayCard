package org.bonal.birthdaycard.presentation.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.data.model.BirthdayHost
import org.bonal.birthdaycard.presentation.BirthdayGuestCardViewState
import org.bonal.birthdaycard.presentation.BirthdayViewModel
import org.bonal.birthdaycard.presentation.theme.BirthdayCardTheme

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
                content = {
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

    rememberScrollState(0)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            .heightIn(200.dp, 380.dp)
            .widthIn(300.dp, 600.dp)
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
                    message?.let {
                        Row(
                            modifier = Modifier
                                .weight(1f, true)
                                .height(0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BirthdayHeaderMessage(message)
                        }
                    }
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
private fun BirthdayHeaderMessage(message: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
        text = message,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Justify,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1
    )
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
    val painter = rememberCoilPainter(backgroundImage)
    Image(
        painter = painter,
        modifier = Modifier.fillMaxSize(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
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
        .size(100.dp, 100.dp)
        .clip(CircleShape)
    birthdayHost.pictureUrl?.let {
        val painter = rememberCoilPainter(it)
        Image(
            painter = painter,
            modifier = imageModifier,
            contentDescription = birthdayHost.name,
            contentScale = ContentScale.Crop,
        )
        when (painter.loadState) {
            is ImageLoadState.Loading -> {
                // Display a circular progress indicator whilst loading
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            is ImageLoadState.Error -> {
                PlaceHolderImage(
                    modifier = imageModifier,
                    tintColor = MaterialTheme.colors.onPrimary
                )
            }
            else -> Unit
        }
    } ?: PlaceHolderImage(imageModifier, tintColor = MaterialTheme.colors.onPrimary)
}

@Composable
private fun semiTransparentBackground(): Color =
    MaterialTheme.colors.primary.copy(alpha = 0.4f)

@Preview
@Composable
fun HostSectionPreview() {
    HostSection(
        birthdayHost = BirthdayHost(name = "Bob", age = 70)
    )
}