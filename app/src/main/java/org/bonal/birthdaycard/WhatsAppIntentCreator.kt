package org.bonal.birthdaycard

import android.content.Intent
import android.net.Uri

class WhatsAppIntentCreator(private val defaultMessage: String? = null) {

    fun createWhatsAppMessageIntent(
        phoneNumber: String?,
        message: String? = defaultMessage
    ): Intent? {
        if (phoneNumber != null) {
            val whatsAppLink =
                Uri.parse("https://wa.me/$phoneNumber?text=$message")
            return Intent(Intent.ACTION_VIEW, whatsAppLink).apply {
                setPackage("com.whatsapp")
            }
        }
        return null
    }
}