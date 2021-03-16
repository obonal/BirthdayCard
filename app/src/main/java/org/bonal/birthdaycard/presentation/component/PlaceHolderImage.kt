package org.bonal.birthdaycard.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import org.bonal.birthdaycard.R

@Composable
fun PlaceHolderImage(
    modifier: Modifier,
    @DrawableRes vectorIconRes: Int = R.drawable.ic_launcher_foreground,
    contentDescription: String? = null,
    tintColor: Color = MaterialTheme.colors.primary) {
    Image(
        modifier = modifier,
        contentDescription = contentDescription,
        painter = painterResource(id = vectorIconRes),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(tintColor)
    )
}