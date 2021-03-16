package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.data.model.BirthdayData
import org.bonal.birthdaycard.data.model.BirthdayGalleryResponse
import org.bonal.birthdaycard.data.network.BirthdayCardApiService
import javax.inject.Inject

class BirthdayRepository @Inject constructor(
    private val birthdayCardApiService: BirthdayCardApiService) {

    suspend fun getBirthdayData(): BirthdayData = birthdayCardApiService.getBirthdayCardContent()

    suspend fun getGalleryData(): BirthdayGalleryResponse = birthdayCardApiService.getBirthdayMemories()
}