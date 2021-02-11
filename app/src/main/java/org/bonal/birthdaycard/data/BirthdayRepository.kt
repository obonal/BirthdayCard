package org.bonal.birthdaycard.data

import org.bonal.birthdaycard.model.BirthdayData
import org.bonal.birthdaycard.model.BirthdayGalleryResponse
import org.bonal.birthdaycard.network.BirthdayCardApiService
import javax.inject.Inject

class BirthdayRepository @Inject constructor(
    private val birthdayCardApiService: BirthdayCardApiService) {

    suspend fun getBirthdayData(): BirthdayData = birthdayCardApiService.getBirthdayCardContent()

    suspend fun getGalleryData(): BirthdayGalleryResponse = birthdayCardApiService.getBirthdayMemories()
}