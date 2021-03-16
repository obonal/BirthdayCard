package org.bonal.birthdaycard.data.model

import com.squareup.moshi.Json

data class BirthdayHost(
    @field:Json(name = "name")
    val name: String,
    val age: Int,
    val pictureUrl: String? = null
)
