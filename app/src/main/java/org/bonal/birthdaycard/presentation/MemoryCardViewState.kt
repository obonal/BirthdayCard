package org.bonal.birthdaycard.presentation

sealed class MemoryCardViewState(val title: String? = null,
                                 val description: String? = null) {
    class TextCardState(
        title: String? = null,
        description: String? = null
    ) : MemoryCardViewState(title, description)

    class PhotoCardState(
        title: String ?= null,
        description: String? = null,
        val pictureUrl: String
    ) : MemoryCardViewState(title, description)

     class VideoCardState(
        title: String? = null,
        description: String? = null,
        val videoUrl: String
    ) : MemoryCardViewState(title, description)
}