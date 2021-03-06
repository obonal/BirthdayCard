package org.bonal.birthdaycard.presentation

import org.bonal.birthdaycard.presentation.component.GuestCardAction

data class BirthdayGuestCardViewState(
    val name: String,
    val message: String?,
    val pictureUrl: String? = null,
    val video: String? = null,
    val secondaryAction: GuestCardAction? = null
) {

    fun hasActions(): Boolean = video != null || secondaryAction != null

}
