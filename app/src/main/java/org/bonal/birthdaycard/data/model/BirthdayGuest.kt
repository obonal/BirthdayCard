package org.bonal.birthdaycard.data.model

data class BirthdayGuest(
    val name: String,
    val message: String? = null,
    val pictureUrl: String? = null,
    val video: String? = null,
    val phoneNumber: String? = null)
