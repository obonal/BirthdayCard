package org.bonal.birthdaycard.viewmodel

sealed class MemoryCardViewState(val title: String,
                                 val description: String? = null) {
    class TextCardState(
        title: String,
        description: String? = null
    ) : MemoryCardViewState(title, description)

    class PhotoCardState(
        title: String,
        description: String? = null,
        val pictureUrl: String
    ) : MemoryCardViewState(title, description)

     class VideoCardState(
        title: String,
        description: String? = null,
        val videoUrl: String
    ) : MemoryCardViewState(title, description)
}