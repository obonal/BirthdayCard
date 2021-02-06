package org.bonal.birthdaycard.model

data class BirthdayGalleryResponse(
    val headerText: String?,
    val galleryButtonLabel: String?,
    val galleryViewTitle: String?,
    val memories: List<BirthdayMemory>
)