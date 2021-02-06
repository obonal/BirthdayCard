package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayData
import org.bonal.birthdaycard.network.BirthdayCardApiService
import javax.inject.Inject

class FetchBirthdayDataUseCase @Inject constructor(private val birthdayCardContentApiService: BirthdayCardApiService) {

    suspend fun fetchBirthdayData(): BirthdayData {
        return birthdayCardContentApiService.getBirthdayCardContent()
    }
}
