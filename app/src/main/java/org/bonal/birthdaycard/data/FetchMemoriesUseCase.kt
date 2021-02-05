package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayMemoryResponse
import org.bonal.birthdaycard.network.BirthdayMemoriesApiService
import javax.inject.Inject

class FetchMemoriesUseCase @Inject constructor(private val birthdayMemoriesApiService: BirthdayMemoriesApiService) {

    suspend fun fetchBirthdayMemories(): BirthdayMemoryResponse {
        return birthdayMemoriesApiService.getBirthdayMemories()
    }
}
