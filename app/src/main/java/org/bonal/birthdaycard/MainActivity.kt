package org.bonal.birthdaycard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.bonal.birthdaycard.presentation.component.HomePage
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

