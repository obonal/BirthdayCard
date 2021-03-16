package org.bonal.birthdaycard.data.network

import org.bonal.birthdaycard.data.model.BirthdayData
import org.bonal.birthdaycard.data.model.BirthdayGalleryResponse
import retrofit2.http.GET

interface BirthdayCardApiService {
    @GET("birthday_card_info.json")
    suspend fun getBirthdayCardContent() : BirthdayData

    @GET("birthday_memories.json")
    suspend fun getBirthdayMemories() : BirthdayGalleryResponse
}