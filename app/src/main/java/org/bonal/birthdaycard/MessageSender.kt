package org.bonal.birthdaycard

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

interface MessageSender {
    fun sendMessage(phoneNumber: String?, message: String? = null)
    val messengerLabel: String
}

class FakeMessageSender : MessageSender {
    override fun sendMessage(phoneNumber: String?, message: String?) = Unit
    override val messengerLabel: String = "Message"
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

    override val messengerLabel: String
        get() = context.getString(R.string.whatsapp_messenger_label)

}