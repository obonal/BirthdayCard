package org.bonal.birthdaycard.domain

import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.BirthdayData
import javax.inject.Inject

class FetchBirthdayDataUseCase @Inject constructor(private val birthdayRepository: BirthdayRepository) {

    suspend fun fetchBirthdayData(): BirthdayData {
        return birthdayRepository.getBirthdayData()
    }
}
