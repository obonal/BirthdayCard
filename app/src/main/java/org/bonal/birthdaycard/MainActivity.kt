package org.bonal.birthdaycard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import org.bonal.birthdaycard.component.HomePage
import org.bonal.birthdaycard.model.defaultBirthdayData

class MainActivity : AppCompatActivity() {

    private val messageSender = WhatsAppMessageSender(activity = this)

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

