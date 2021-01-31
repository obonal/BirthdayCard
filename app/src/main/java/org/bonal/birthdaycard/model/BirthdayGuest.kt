package org.bonal.birthdaycard.model

import androidx.annotation.DrawableRes

data class BirthdayGuest(
    val name: String,
    val message: String = "Happy Birthday!",
    val pictureUrl: String? = null,
    val video: String? = null,
    val phoneNumber: String? = null)
