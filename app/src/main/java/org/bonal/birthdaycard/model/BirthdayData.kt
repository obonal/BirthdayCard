package org.bonal.birthdaycard.model

data class BirthdayData(
    val birthdayHost: BirthdayHost,
    val guestList: List<BirthdayGuest>,
    val birthdayCardMessage: String,
    val birthdayCardBackground: String
)