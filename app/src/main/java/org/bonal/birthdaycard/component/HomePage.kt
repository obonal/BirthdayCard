package org.bonal.birthdaycard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import org.bonal.birthdaycard.viewmodel.BirthdayGuestCardModel
import org.bonal.birthdaycard.viewmodel.BirthdayViewModel

@Composable
fun HomePage(viewModel: BirthdayViewModel, onHostClicked: () -> Unit) {
    BirthdayCardTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    val title = stringResource(id = R.string.toolbar_title)
                    TopAppBar(
                        title = { Text(text = title) }
                    )
                },
                bodyContent = {
                    BirthdayFeed(
                        viewModel = viewModel,
                        onHostClicked = onHostClicked
                    )
                }
            )

        }
    }
}

@Composable
private fun BirthdayFeed(
    viewModel: BirthdayViewModel,
    onHostClicked: () -> Unit
) {
    val birthdayHost: BirthdayHost? by viewModel.birthdayHost.observeAsState()
    val guestList: List<BirthdayGuestCardModel> by viewModel.guestList.observeAsState(emptyList())
    val birthdayCardMessage: String by viewModel.birthdayCardMessage.observeAsState("")
    val birthdayCardBackground: String by viewModel.birthdayCardBackground.observeAsState("")

    rememberScrollState(0f)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            BirthdayCardHeader(
                birthdayHost = birthdayHost,
                message = birthdayCardMessage,
                backgroundImage = birthdayCardBackground,
                onHostClicked = onHostClicked
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
    message: String,
    backgroundImage: String?,
    onHostClicked: () -> Unit
) {
    Card(
        Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .clickable { onHostClicked() }
            .fillMaxWidth(),
        elevation = 8.dp,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Box(contentAlignment = Alignment.Center) {
            cardBackgroundImage(backgroundImage)
            Row(
                Modifier
                    .wrapContentHeight()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.background(semiTransparentBackground()),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    HostSection(birthdayHost)
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.8f)
                            .padding(16.dp),
                        text = message,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
private fun cardBackgroundImage(backgroundImage: String?) {
    backgroundImage ?: return
    CoilImage(
        modifier = Modifier
            .fillMaxSize(),
        data = backgroundImage,
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
            .fillMaxWidth()
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
    MaterialTheme.colors.primary.copy(alpha = 0.2f)

@Preview
@Composable
fun HostSectionPreview() {
    HostSection(
        birthdayHost = BirthdayHost(name = "Bob", age = 70)
    )
}