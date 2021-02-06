package org.bonal.birthdaycard.network

import org.bonal.birthdaycard.model.BirthdayData
import org.bonal.birthdaycard.model.BirthdayGalleryResponse
import retrofit2.http.GET

interface BirthdayCardApiService {
    @GET("birthday_card_info.json")
    suspend fun getBirthdayCardContent() : BirthdayData

    @GET("birthday_memories.json")
    suspend fun getBirthdayMemories() : BirthdayGalleryResponse
}