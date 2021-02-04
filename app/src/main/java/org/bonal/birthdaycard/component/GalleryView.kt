package org.bonal.birthdaycard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.bonal.birthdaycard.ui.theme.BirthdayCardTheme

@Composable
fun GalleryView() {
    BirthdayCardTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(
                    text = "TODO",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
}

@Preview
@Composable
fun GalleryViewPreview() {
    GalleryView()
}