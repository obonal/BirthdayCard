package org.bonal.birthdaycard.model

data class BirthdayGuest(
    val name: String,
    val message: String? = null,
    val pictureUrl: String? = null,
    val video: String? = null,
    val phoneNumber: String? = null)
