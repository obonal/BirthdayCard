package org.bonal.birthdaycard.data.model

data class BirthdayGalleryResponse(
    val galleryViewTitle: String? = null,
    val memories: List<BirthdayMemory>
)