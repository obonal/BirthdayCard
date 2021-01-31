package org.bonal.birthdaycard

import android.content.Intent
import android.net.Uri
import kotlin.text.StringBuilder

class WhatsAppIntentCreator(private val defaultMessage: String? = null) {

    fun createWhatsAppMessageIntent(
        phoneNumber: String?,
        message: String? = defaultMessage
    ): Intent? = phoneNumber?.let { number ->
        val builder: StringBuilder = StringBuilder("https://wa.me/$number")
        if (message != null) {
            builder.append("?text=$message")
        }
        val whatsAppLink = Uri.parse(builder.toString())
        return Intent(Intent.ACTION_VIEW, whatsAppLink).apply {
            setPackage("com.whatsapp")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        }
    }
}