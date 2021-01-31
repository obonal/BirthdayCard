package org.bonal.birthdaycard.network

import org.bonal.birthdaycard.model.BirthdayData
import retrofit2.http.GET

interface BirthdayCardContentApiService {
    @GET("birthday_card_info.json")
    suspend fun getBirthdayCardContent() : BirthdayData
}