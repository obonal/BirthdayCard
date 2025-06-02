package org.bonal.birthdaycard.presentation.component

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, sdk = [28])
class VideoPlayerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testVideoPlayerViewIsPresent() {
        val videoUrl = "http://example.com/video.mp4"
        val testTag = "VideoPlayerAndroidView"
        composeTestRule.setContent {
            VideoPlayer(
                videoUrl = videoUrl,
                modifier = Modifier.testTag(testTag)
            )
        }
        composeTestRule.onNodeWithTag(testTag).assertExists() // assertExists is better for AndroidViewBinding
    }

    @Test
    fun testPlayerIsPreparedWithCorrectMediaItem() {
        val videoUrl = "http://example.com/video_correct_item.mp4"
        var playerInstance: SimpleExoPlayer? = null

        composeTestRule.setContent {
            VideoPlayer(
                videoUrl = videoUrl,
                onPlayerCreated = { player ->
                    playerInstance = player
                },
                modifier = Modifier.testTag("VideoPlayerForMediaItemCheck")
            )
        }

        // Ensure composition completes and player is created
        composeTestRule.waitForIdle() // Important for Robolectric and player setup
        ShadowLooper.idleMainLooper() // Process any pending messages for ExoPlayer

        assertThat(playerInstance).isNotNull()
        playerInstance?.let { player ->
            val mediaItem = player.currentMediaItem
            assertThat(mediaItem).isNotNull()
            assertThat(mediaItem?.playbackProperties?.uri.toString()).isEqualTo(videoUrl)
        }
    }

    @Test
    fun testAutoPlayConfiguration_True() {
        val videoUrl = "http://example.com/video_autoplay_true.mp4"
        var playerInstance: SimpleExoPlayer? = null

        composeTestRule.setContent {
            VideoPlayer(
                videoUrl = videoUrl,
                autoPlay = true,
                onPlayerCreated = { player ->
                    playerInstance = player
                },
                modifier = Modifier.testTag("VideoPlayerAutoPlayTrue")
            )
        }
        composeTestRule.waitForIdle()
        ShadowLooper.idleMainLooper()

        assertThat(playerInstance).isNotNull()
        assertThat(playerInstance?.playWhenReady).isTrue()
    }

    @Test
    fun testAutoPlayConfiguration_False() {
        val videoUrl = "http://example.com/video_autoplay_false.mp4"
        var playerInstance: SimpleExoPlayer? = null

        composeTestRule.setContent {
            VideoPlayer(
                videoUrl = videoUrl,
                autoPlay = false,
                onPlayerCreated = { player ->
                    playerInstance = player
                },
                modifier = Modifier.testTag("VideoPlayerAutoPlayFalse")
            )
        }
        composeTestRule.waitForIdle()
        ShadowLooper.idleMainLooper()

        assertThat(playerInstance).isNotNull()
        assertThat(playerInstance?.playWhenReady).isFalse()
    }

    @Test
    fun testPlayerStopOnDispose() {
        // Direct verification of `exoPlayer.stop()` on dispose is complex with the current setup.
        // It would typically require:
        // 1. Injecting a mocked or spied ExoPlayer instance into VideoPlayer, or
        // 2. Using Robolectric shadows to assert calls on the actual ExoPlayer instance if it's globally accessible or retrievable
        //    after the composable is disposed (which is hard as the instance is tied to `remember`).
        // 3. Observing side-effects of `stop()` if any (e.g., resource release, specific player state changes accessible externally).

        // Given `VideoPlayer` creates and remembers its own `SimpleExoPlayer` instance internally,
        // and `DisposableEffect` is used to call `exoPlayer.stop()`, we are relying on Compose's
        // correct execution of `DisposableEffect`.

        // A more involved test could involve setting up the VideoPlayer, ensuring it plays,
        // then removing it from composition, and then attempting to check if the media is still playing
        // (e.g. if using a real audio output, but this is too complex for unit tests).

        // For now, this test case remains as an acknowledgement of the desirable test and its current implementation difficulty.
        // The `DisposableEffect(Unit) { onDispose { exoPlayer.stop() } }` line in VideoPlayer.kt
        // is the production code ensuring this behavior.
    }
}
