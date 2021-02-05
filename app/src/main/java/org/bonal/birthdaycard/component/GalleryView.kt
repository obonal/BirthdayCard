package org.bonal.birthdaycard.component

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
import org.bonal.birthdaycard.model.BirthdayMemory
import org.bonal.birthdaycard.ui.theme.BirthdayCardTheme
import org.bonal.birthdaycard.viewmodel.BirthdayMemoriesViewModel

@Composable
fun GalleryView(viewModel: BirthdayMemoriesViewModel) {
    val memories: List<BirthdayMemory> by viewModel.memoriesList.observeAsState(emptyList())

    BirthdayCardTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    val title = stringResource(id = R.string.title_activity_gallery)
                    TopAppBar(
                        title = { Text(text = title) }
                    )
                },
                bodyContent = {
                    rememberScrollState(0f)
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

//@Preview
//@Composable
//fun GalleryViewPreview() {
//    GalleryView(memoriesViewModel)
//}