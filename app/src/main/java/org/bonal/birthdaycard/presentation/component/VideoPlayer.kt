package org.bonal.birthdaycard.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import org.bonal.birthdaycard.databinding.VideoPlayerLayoutBinding

package org.bonal.birthdaycard.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier // Added import
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import org.bonal.birthdaycard.databinding.VideoPlayerLayoutBinding

@Composable
fun VideoPlayer(
    videoUrl: String,
    autoPlay: Boolean = false,
    controllerAutoShow: Boolean = !autoPlay,
    modifier: Modifier = Modifier, // Added modifier parameter
    onPlayerCreated: (SimpleExoPlayer) -> Unit = {} // Added callback for player instance
) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().also {
            onPlayerCreated(it) // Invoke callback when player is created
        }
    }

    DisposableEffect(Unit) { onDispose(onDisposeEffect = { exoPlayer.stop() }) }

    // Gateway to legacy Android Views through XML inflation.
    AndroidViewBinding(VideoPlayerLayoutBinding::inflate, modifier = modifier) { // Apply modifier
        val exoPlayerView = playerView
        exoPlayerView.player = exoPlayer
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayerView.hideController()
        exoPlayerView.controllerAutoShow = controllerAutoShow
        if (autoPlay) {
            exoPlayer.play()
        }
    }
}