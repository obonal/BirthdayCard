package org.bonal.birthdaycard

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface MessageSender {
    fun sendMessage(phoneNumber: String, message: String? = null)
    val messengerLabel: String
}

class WhatsAppMessageSender @Inject constructor(@ApplicationContext private val context: Context) :
    MessageSender {
    private val whatsAppIntentCreator: WhatsAppIntentCreator by lazy {
        WhatsAppIntentCreator(defaultMessage = context.getString(R.string.default_whatsapp_message))
    }

    override fun sendMessage(phoneNumber: String, message: String?) {
        whatsAppIntentCreator.createWhatsAppMessageIntent(phoneNumber, message)?.let {
            context.startActivity(it)
        }
    }

    override val messengerLabel: String
        get() = context.getString(R.string.whatsapp_messenger_label)

}