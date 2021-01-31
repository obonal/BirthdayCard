package org.bonal.birthdaycard.viewmodel

import org.bonal.birthdaycard.component.GuestCardAction

data class BirthdayGuestCardModel(
    val name: String,
    val message: String?,
    val pictureUrl: String? = null,
    val video: String? = null,
    val secondaryAction: GuestCardAction? = null
) {

    fun hasActions(): Boolean = video != null || secondaryAction != null

}
