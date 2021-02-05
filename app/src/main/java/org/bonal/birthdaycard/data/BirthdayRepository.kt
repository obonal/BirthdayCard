package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayData
import org.bonal.birthdaycard.model.BirthdayMemory
import javax.inject.Inject

class BirthdayRepository @Inject constructor(
    private val fetchBirthdayDataUseCase: FetchBirthdayDataUseCase,
    private val fetchMemoriesUseCase: FetchMemoriesUseCase) {
    suspend fun getBirthdayData(): BirthdayData = fetchBirthdayDataUseCase.fetchBirthdayData()

    suspend fun getMemories(): List<BirthdayMemory> =
        fetchMemoriesUseCase.fetchBirthdayMemories().memories
}