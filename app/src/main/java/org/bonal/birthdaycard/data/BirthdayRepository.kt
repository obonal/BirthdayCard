package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayData
import javax.inject.Inject

class BirthdayRepository @Inject constructor(private val fetchBirthdayDataUseCase: FetchBirthdayDataUseCase) {

    suspend fun getBirthdayData(): BirthdayData = fetchBirthdayDataUseCase.fetchBirthdayData()
}