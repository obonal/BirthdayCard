package org.bonal.birthdaycard.model

data class BirthdayGalleryResponse(
    val galleryViewTitle: String? = null,
    val memories: List<BirthdayMemory>
)