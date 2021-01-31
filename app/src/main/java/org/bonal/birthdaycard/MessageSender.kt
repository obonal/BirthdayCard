package org.bonal.birthdaycard

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

interface MessageSender {
    fun sendMessage(phoneNumber: String?, message: String? = null)
}


class WhatsAppMessageSender @Inject constructor(@ActivityContext private val context: Context) :
    MessageSender {
    private val whatsAppIntentCreator: WhatsAppIntentCreator by lazy {
        WhatsAppIntentCreator(defaultMessage = context.getString(R.string.default_whatsapp_message))
    }

    override fun sendMessage(phoneNumber: String?, message: String?) {
        whatsAppIntentCreator.createWhatsAppMessageIntent(phoneNumber, message)?.let {
            context.startActivity(it)
        }
    }

}