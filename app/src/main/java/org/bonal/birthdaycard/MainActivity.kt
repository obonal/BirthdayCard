package org.bonal.birthdaycard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.bonal.birthdaycard.component.HomePage
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
            HomePage(
                viewModel = birthdayViewModel,
                messageSender = messageSender
            )
        }
    }
}

