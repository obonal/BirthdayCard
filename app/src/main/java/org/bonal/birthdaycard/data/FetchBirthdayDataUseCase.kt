package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayData
import org.bonal.birthdaycard.network.BirthdayCardContentApiService
import javax.inject.Inject

class FetchBirthdayDataUseCase @Inject constructor(private val birthdayCardContentApiService: BirthdayCardContentApiService) {

    suspend fun fetchBirthdayData(): BirthdayData {
        return birthdayCardContentApiService.getBirthdayCardContent()
    }
}
