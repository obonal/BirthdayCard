package org.bonal.birthdaycard.data.model

data class BirthdayData(
    val birthdayHost: BirthdayHost,
    val guestList: List<BirthdayGuest>,
    val birthdayCardMessage: String,
    val birthdayCardBackground: String,
    val galleryButtonLabel: String?
)