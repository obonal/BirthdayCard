package org.bonal.birthdaycard.presentation.component

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import org.bonal.birthdaycard.R
import org.bonal.birthdaycard.domain.model.BirthdayHost
import org.bonal.birthdaycard.presentation.BirthdayGuestCardViewState
import org.bonal.birthdaycard.presentation.BirthdayViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Fake ViewModel for testing HomePage
class FakeBirthdayViewModel : BirthdayViewModel(ApplicationProvider.getApplicationContext()) {
    val birthdayHostLiveData = MutableLiveData<BirthdayHost>()
    val guestListLiveData = MutableLiveData<List<BirthdayGuestCardViewState>>()
    val birthdayCardMessageLiveData = MutableLiveData<String>()
    val galleryButtonLabelLiveData = MutableLiveData<String>()
    val birthdayCardBackgroundColorLiveData = MutableLiveData<String>()
    val birthdayCardBackgroundImageLiveData = MutableLiveData<String>()

    override fun getBirthdayHost() = birthdayHostLiveData
    override fun getGuestList() = guestListLiveData
    override fun getBirthdayCardTitleMessage() = birthdayCardMessageLiveData
    override fun getGalleryButtonLabel() = galleryButtonLabelLiveData
    override fun getBirthdayCardBackgroundColor() = birthdayCardBackgroundColorLiveData
    override fun getBirthdayCardBackgroundImage() = birthdayCardBackgroundImageLiveData

    // Keep these as no-ops for most HomePage UI tests unless their interaction is specifically tested
    override fun itemClicked(item: BirthdayGuestCardViewState) {}
    override fun loadBirthdayData() {}
    override fun changeCardBackground() {}
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, sdk = [28])
class HomePageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private lateinit var fakeViewModel: FakeBirthdayViewModel

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        fakeViewModel = FakeBirthdayViewModel()
    }

    private fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    @Test
    fun testHostInformationDisplayed() {
        val hostName = "Test Host"
        val host = BirthdayHost(name = hostName, pictureUrl = null)
        fakeViewModel.birthdayHostLiveData.postValue(host)
        fakeViewModel.galleryButtonLabelLiveData.postValue("Gallery") // Default to ensure button is there

        composeTestRule.setContent {
            HomePage(
                viewModel = fakeViewModel,
                navigateToMemories = {}
            )
        }

        // Verify TopAppBar title
        val expectedAppBarTitle = getString(R.string.birthday_card_title, hostName)
        composeTestRule.onNodeWithText(expectedAppBarTitle).assertIsDisplayed()

        // Verify host name is displayed (e.g., in HostSection)
        // This assertion is a bit broad; if hostName appears in multiple places, it might pass unexpectedly.
        // However, HostSection specifically displays the name prominently.
        composeTestRule.onNodeWithText(hostName, substring = true).assertIsDisplayed()

        // Verify host image/placeholder is displayed using its content description
        composeTestRule.onNodeWithContentDescription(hostName).assertIsDisplayed()
    }

    @Test
    fun testGuestListDisplayed() {
        val guests = listOf(
            BirthdayGuestCardViewState(name = "Guest One", message = "Msg 1"),
            BirthdayGuestCardViewState(name = "Guest Two", message = "Msg 2")
        )
        // Post default values for other LiveData that HomePage might observe
        fakeViewModel.birthdayHostLiveData.postValue(BirthdayHost(name = "Default Host"))
        fakeViewModel.galleryButtonLabelLiveData.postValue("Gallery")
        fakeViewModel.guestListLiveData.postValue(guests)


        composeTestRule.setContent {
            HomePage(
                viewModel = fakeViewModel,
                navigateToMemories = {}
            )
        }

        composeTestRule.onNodeWithText("Guest One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Guest Two").assertIsDisplayed()
    }

    @Test
    fun testNavigateToMemoriesCallback() {
        var callbackInvoked = false
        val galleryButtonText = "View Photo Memories" // Using a more descriptive custom label
        fakeViewModel.galleryButtonLabelLiveData.postValue(galleryButtonText)
        // Post default values for other LiveData to ensure HomePage composes
        fakeViewModel.birthdayHostLiveData.postValue(BirthdayHost(name = "Default Host"))
        fakeViewModel.guestListLiveData.postValue(emptyList())


        composeTestRule.setContent {
            HomePage(
                viewModel = fakeViewModel,
                navigateToMemories = { callbackInvoked = true }
            )
        }

        composeTestRule.onNodeWithText(galleryButtonText).assertIsDisplayed()
        composeTestRule.onNodeWithText(galleryButtonText).performClick()

        assert(callbackInvoked) { "navigateToMemories callback was not invoked." }
    }
}
