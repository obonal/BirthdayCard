package org.bonal.birthdaycard.network

import org.bonal.birthdaycard.model.BirthdayGalleryResponse
import retrofit2.http.GET

interface BirthdayMemoriesApiService {
    @GET("birthday_memories.json")
    suspend fun getBirthdayMemories() : BirthdayGalleryResponse
}