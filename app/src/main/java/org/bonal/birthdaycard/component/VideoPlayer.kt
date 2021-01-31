package org.bonal.birthdaycard.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import org.bonal.birthdaycard.databinding.VideoPlayerLayoutBinding

@Composable
fun VideoPlayer(videoUrl: String) {
    // This is the official way to access current context from Composable functions
    val context = AmbientContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember { SimpleExoPlayer.Builder(context).build() }

//    DisposableEffect(Unit) { onDispose(onDisposeEffect = { exoPlayer.stop() }) }
    onDispose(callback = { exoPlayer.stop() })

    // Gateway to legacy Android Views through XML inflation.
    AndroidViewBinding(VideoPlayerLayoutBinding::inflate) {
        val exoPlayerView = playerView
        exoPlayerView.player = exoPlayer
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }
}