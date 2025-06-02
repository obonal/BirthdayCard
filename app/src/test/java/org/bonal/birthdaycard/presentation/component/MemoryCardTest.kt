package org.bonal.birthdaycard.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.bonal.birthdaycard.presentation.MemoryCardViewState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, sdk = [28])
class MemoryCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTextCard_DisplaysTitleAndDescription() {
        val title = "My Text Title"
        val description = "This is a text description."
        val viewState = MemoryCardViewState.TextCardState(
            title = title,
            description = description
        )

        composeTestRule.setContent {
            MemoryCard(memory = viewState)
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
    }

    @Test
    fun testPhotoCard_DisplaysImageAndTexts() {
        val title = "My Photo Title"
        val description = "This is a photo description."
        val imageUrl = "http://example.com/photo.jpg"
        val viewState = MemoryCardViewState.PhotoCardState(
            title = title,
            description = description,
            photoUrl = imageUrl
        )

        composeTestRule.setContent {
            MemoryCard(memory = viewState)
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
        // Assuming a testTag "MemoryCardPhoto" will be added to the Image composable in MemoryCard.kt
        composeTestRule.onNodeWithTag("MemoryCardPhoto").assertIsDisplayed()
    }

    @Test
    fun testVideoCard_DisplaysVideoPlayerAndTexts() {
        val title = "My Video Title"
        val description = "This is a video description."
        val videoUrl = "http://example.com/video.mp4"
        val viewState = MemoryCardViewState.VideoCardState(
            title = title,
            description = description,
            videoUrl = videoUrl
        )

        composeTestRule.setContent {
            MemoryCard(memory = viewState)
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
        // Assuming a testTag "MemoryCardVideoPlayer" will be added to the VideoPlayer composable in MemoryCard.kt
        composeTestRule.onNodeWithTag("MemoryCardVideoPlayer").assertIsDisplayed()
    }
}
