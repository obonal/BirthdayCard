package org.bonal.birthdaycard.presentation.component

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.presentation.BirthdayMemoriesViewModel
import org.bonal.birthdaycard.presentation.MemoryCardViewState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class FakeBirthdayMemoriesViewModel : BirthdayMemoriesViewModel(ApplicationProvider.getApplicationContext()) {
    val memoriesListLiveData = MutableLiveData<List<MemoryCardViewState>>()
    val galleryViewTitleLiveData = MutableLiveData<String?>() // Title can be nullable

    override fun getMemoriesList() = memoriesListLiveData
    override fun getGalleryViewTitle() = galleryViewTitleLiveData
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, sdk = [28])
class GalleryViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private lateinit var fakeViewModel: FakeBirthdayMemoriesViewModel

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        fakeViewModel = FakeBirthdayMemoriesViewModel()
        // Post default empty list to avoid null issues if GalleryView observes immediately
        fakeViewModel.memoriesListLiveData.postValue(emptyList())
    }

    private fun getString(resId: Int): String {
        return context.getString(resId)
    }

    @Test
    fun testGalleryTitleDisplayed_CustomTitle() {
        val customTitle = "My Awesome Gallery"
        fakeViewModel.galleryViewTitleLiveData.postValue(customTitle)

        composeTestRule.setContent {
            GalleryView(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText(customTitle).assertIsDisplayed()
    }

    @Test
    fun testGalleryTitleDisplayed_DefaultTitle_WhenNull() {
        fakeViewModel.galleryViewTitleLiveData.postValue(null)
        val defaultTitle = getString(R.string.toolbar_title) // "Happy Birthday"

        composeTestRule.setContent {
            GalleryView(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText(defaultTitle).assertIsDisplayed()
    }

    @Test
    fun testGalleryTitleDisplayed_DefaultTitle_WhenEmpty() {
        fakeViewModel.galleryViewTitleLiveData.postValue("")
        val defaultTitle = getString(R.string.toolbar_title) // "Happy Birthday"

        composeTestRule.setContent {
            GalleryView(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText(defaultTitle).assertIsDisplayed()
    }

    @Test
    fun testMemoryListDisplayed() {
        val memories = listOf(
            MemoryCardViewState.TextCardState(title = "Text Memory", description = "Text Desc"),
            MemoryCardViewState.PhotoCardState(title = "Photo Memory", photoUrl = "url1", description = "Photo Desc"),
            MemoryCardViewState.VideoCardState(title = "Video Memory", videoUrl = "url2", description = "Video Desc")
        )
        fakeViewModel.memoriesListLiveData.postValue(memories)
        fakeViewModel.galleryViewTitleLiveData.postValue("Memory List Test") // Set a title

        composeTestRule.setContent {
            GalleryView(viewModel = fakeViewModel)
        }

        // Verify Text Card content
        composeTestRule.onNodeWithText("Text Memory").assertIsDisplayed()
        composeTestRule.onNodeWithText("Text Desc").assertIsDisplayed()

        // Verify Photo Card content (title/desc and presence of image via testTag)
        composeTestRule.onNodeWithText("Photo Memory").assertIsDisplayed()
        composeTestRule.onNodeWithText("Photo Desc").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MemoryCardPhoto", useUnmergedTree = true).assertIsDisplayed()


        // Verify Video Card content (title/desc and presence of video player via testTag)
        composeTestRule.onNodeWithText("Video Memory").assertIsDisplayed()
        composeTestRule.onNodeWithText("Video Desc").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MemoryCardVideoPlayer", useUnmergedTree = true).assertIsDisplayed()
    }
}
