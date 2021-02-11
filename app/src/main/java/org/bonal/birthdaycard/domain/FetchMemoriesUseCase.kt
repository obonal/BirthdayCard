package org.bonal.birthdaycard.domain

import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.BirthdayGalleryResponse
import javax.inject.Inject

class FetchMemoriesUseCase @Inject constructor(private val birthdayRepository: BirthdayRepository) {

    suspend fun fetchBirthdayMemories(): BirthdayGalleryResponse {
        return birthdayRepository.getGalleryData()
    }
}
