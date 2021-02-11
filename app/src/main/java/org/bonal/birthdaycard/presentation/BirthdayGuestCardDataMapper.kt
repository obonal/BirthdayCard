package org.bonal.birthdaycard.presentation

import org.bonal.birthdaycard.MessageSender
import org.bonal.birthdaycard.component.GuestCardAction
import org.bonal.birthdaycard.model.BirthdayGuest
import javax.inject.Inject

class BirthdayGuestCardDataMapper @Inject constructor(private val messageSender: MessageSender) {
    fun map(guest: BirthdayGuest) =
        BirthdayGuestCardViewState(name = guest.name,
            message = guest.message,
            pictureUrl = guest.pictureUrl,
            video = guest.video,
            secondaryAction = guest.phoneNumber?.let { number ->
                GuestCardAction(messageSender.messengerLabel) {
                    messageSender.sendMessage(number)
                }
            })
}