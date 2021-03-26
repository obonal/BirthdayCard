package org.bonal.birthdaycard.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.presentation.BirthdayMemoriesViewModel
import org.bonal.birthdaycard.presentation.MemoryCardViewState
import org.bonal.birthdaycard.presentation.theme.BirthdayCardTheme

@Composable
fun GalleryView(viewModel: BirthdayMemoriesViewModel) {
    val memories: List<MemoryCardViewState> by viewModel.memoriesList.observeAsState(emptyList())
    val galleryViewTitle: String? by viewModel.galleryViewTitle.observeAsState()

    BirthdayCardTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = galleryViewTitle
                                    ?: stringResource(id = R.string.toolbar_title)
                            )
                        }
                    )
                },
                content = {
                    rememberScrollState(0)
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(memories.size) { index ->
                            MemoryCard(memories[index])
                        }
                    }
                }
            )

        }
    }
}