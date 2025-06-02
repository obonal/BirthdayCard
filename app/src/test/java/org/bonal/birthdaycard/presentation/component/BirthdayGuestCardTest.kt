package org.bonal.birthdaycard.presentation.component

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.domain.model.BirthdayGuest
// GuestCardAction is defined in BirthdayGuestCard.kt, so no separate import if in same package logically for test
// import org.bonal.birthdaycard.domain.model.GuestCardAction // If it were in domain.model
import org.bonal.birthdaycard.presentation.BirthdayGuestCardViewState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, sdk = [28])
class BirthdayGuestCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    private fun getString(resId: Int): String {
        return context.getString(resId)
    }

    @Test
    fun testGuestInfoDisplayed() {
        val guestName = "Alex"
        val guestMessage = "Happy Birthday!"
        val viewState = BirthdayGuestCardViewState(
            name = guestName,
            message = guestMessage
        )

        composeTestRule.setContent {
            BirthdayGuestCard(cardViewState = viewState)
        }

        composeTestRule.onNodeWithText(guestName).assertIsDisplayed()
        composeTestRule.onNodeWithText(guestMessage).assertIsDisplayed()
    }

    @Test
    fun testGuestImageDisplayed_NoImageUrl_ShowsPlaceholder() {
        val guestName = "Bob"
        // As per BirthdayGuestCard.kt, contentDescription for placeholder is guestName
        val expectedContentDescription = guestName

        val viewState = BirthdayGuestCardViewState(
            name = guestName,
            message = "Test"
            // pictureUrl is null by default
        )

        composeTestRule.setContent {
            BirthdayGuestCard(cardViewState = viewState)
        }

        composeTestRule.onNodeWithContentDescription(expectedContentDescription).assertIsDisplayed()
    }

    @Test
    fun testGuestImageDisplayed_WithImageUrl_CoilErrorShowsPlaceholder() {
        // Coil's behavior in Robolectric might lead to error state if network calls are not mocked/handled.
        // When ImageLoadState.Error occurs, PlaceHolderImage is shown with guestName as contentDescription.
        val guestName = "Carol"
        val imageUrl = "http://example.com/image.png" // This will likely fail in Robolectric
        val expectedContentDescriptionOnError = guestName


        val viewState = BirthdayGuestCardViewState(
            name = guestName,
            message = "Test",
            pictureUrl = imageUrl
        )

        composeTestRule.setContent {
            BirthdayGuestCard(cardViewState = viewState)
        }
        // We expect the placeholder to be shown due to Coil error/Robolectric limitations
        composeTestRule.onNodeWithContentDescription(expectedContentDescriptionOnError).assertIsDisplayed()
    }


    @Test
    fun testVideoPlayerToggle() {
        val videoUrl = "http://example.com/video.mp4"
        val viewState = BirthdayGuestCardViewState(
            name = "Dave",
            message = "Test",
            video = videoUrl // video is non-null
        )

        composeTestRule.setContent {
            BirthdayGuestCard(viewState = viewState)
        }

        // Initially, video player should not be visible
        composeTestRule.onNodeWithTag("VideoPlayer", useUnmergedTree = true).assertDoesNotExist()

        // Click "Play Video"
        val playButtonText = getString(R.string.play_video_button_label) // "Show Video"
        composeTestRule.onNodeWithText(playButtonText).performClick()

        // Video player should be visible
        composeTestRule.onNodeWithTag("VideoPlayer", useUnmergedTree = true).assertIsDisplayed()
        val stopButtonText = getString(R.string.stop_video_button_label) // "Hide Video"
        composeTestRule.onNodeWithText(stopButtonText).assertIsDisplayed() // Button text changes

        // Click "Stop Video"
        composeTestRule.onNodeWithText(stopButtonText).performClick()

        // Video player should be hidden
        composeTestRule.onNodeWithTag("VideoPlayer", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithText(playButtonText).assertIsDisplayed() // Button text changes back
    }

    @Test
    fun testSecondaryAction() {
        var actionClicked = false
        val actionLabel = "Test Action"
        val guestCardAction = GuestCardAction( // Using the locally defined GuestCardAction
            actionLabel = actionLabel,
            performAction = { actionClicked = true }
        )
        val viewState = BirthdayGuestCardViewState(
            name = "Eve",
            message = "Test",
            secondaryAction = guestCardAction
        )

        composeTestRule.setContent {
            BirthdayGuestCard(cardViewState = viewState)
        }

        composeTestRule.onNodeWithText(actionLabel).assertIsDisplayed()
        composeTestRule.onNodeWithText(actionLabel).performClick()

        assert(actionClicked) { "The secondary action was not performed." }
    }
}

// Definition for GuestCardAction as it's defined within BirthdayGuestCard.kt
// This is for compilation within the test file if it's not accessible otherwise.
// However, since it's public within its file, it should be accessible if packages match
// or through import if BirthdayGuestCard.kt's package is imported.
// For simplicity in this test context, if direct import fails due to build system nuances not visible here,
// re-defining a compatible local version or ensuring proper import is key.
// Given GuestCardAction is public in its file, direct import should ideally work.
// No, it's defined as: class GuestCardAction(val actionLabel: String, val performAction: () -> Unit)
// So the test should be able to use it directly.
