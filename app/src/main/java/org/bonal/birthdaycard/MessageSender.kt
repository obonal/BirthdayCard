package org.bonal.birthdaycard

import android.app.Activity

interface MessageSender {
    fun sendMessage(phoneNumber: String?, message: String? = null)
}

class WhatsAppMessageSender(private val activity: Activity) : MessageSender {
    private val whatsAppIntentCreator: WhatsAppIntentCreator by lazy {
        WhatsAppIntentCreator(defaultMessage = activity.getString(R.string.default_whatsapp_message))
    }

    override fun sendMessage(phoneNumber: String?, message: String?) {
        whatsAppIntentCreator.createWhatsAppMessageIntent(phoneNumber, message)?.let {
            activity.startActivity(it)
        }
    }

}