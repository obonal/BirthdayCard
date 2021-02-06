package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayGalleryResponse
import org.bonal.birthdaycard.network.BirthdayCardApiService
import javax.inject.Inject

class FetchMemoriesUseCase @Inject constructor(private val birthdayCardApiService: BirthdayCardApiService) {

    suspend fun fetchBirthdayMemories(): BirthdayGalleryResponse {
        return birthdayCardApiService.getBirthdayMemories()
    }
}
