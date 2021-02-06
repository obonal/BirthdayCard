package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayGalleryResponse
import org.bonal.birthdaycard.network.BirthdayMemoriesApiService
import javax.inject.Inject

class FetchMemoriesUseCase @Inject constructor(private val birthdayMemoriesApiService: BirthdayMemoriesApiService) {

    suspend fun fetchBirthdayMemories(): BirthdayGalleryResponse {
        return birthdayMemoriesApiService.getBirthdayMemories()
    }
}
