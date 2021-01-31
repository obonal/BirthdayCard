package org.bonal.birthdaycard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.bonal.birthdaycard.component.HomePage
import org.bonal.birthdaycard.model.defaultBirthdayData
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var messageSender: MessageSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePage(
                birthdayData = defaultBirthdayData,
                messageSender = messageSender
            )
        }
    }
}

