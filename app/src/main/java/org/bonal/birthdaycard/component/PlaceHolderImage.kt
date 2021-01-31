package org.bonal.birthdaycard.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import org.bonal.birthdaycard.R

@Composable
fun PlaceHolderImage(modifier: Modifier,
                     @DrawableRes vectorIconRes: Int = R.drawable.ic_launcher_foreground
) {
    Image(
        modifier = modifier,
        imageVector = vectorResource(id = vectorIconRes),
        contentScale = ContentScale.Crop
    )
}