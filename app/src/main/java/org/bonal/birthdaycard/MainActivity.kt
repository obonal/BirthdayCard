package org.bonal.birthdaycard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.CompositionLocal
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.bonal.birthdaycard.component.HomePage
import org.bonal.birthdaycard.presentation.BirthdayViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val birthdayViewModel: BirthdayViewModel by viewModels()

    @Inject
    lateinit var messageSender: MessageSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        birthdayViewModel.loadBirthdayData()
        setContent {
            HomePage(viewModel = birthdayViewModel) {
                startActivity(Intent(this, HostGalleryActivity::class.java))
            }
        }
    }
}

