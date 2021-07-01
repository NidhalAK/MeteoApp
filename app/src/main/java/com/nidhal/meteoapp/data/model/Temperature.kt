package com.nidhal.meteoapp.data.model

import com.squareup.moshi.Json

data class Temperature(
    @Json (name = "temp")
    val temp: Double?,
    @Json (name = "feels_like")
    val feels_like: Double?,
    @Json (name = "temp_min")
    val temp_min: Double?,
    @Json (name = "temp_max")
    val temp_max: Double?,
    @Json (name = "pressure")
    val pressure: Double?,
    @Json (name = "humidity")
    val humidity: Double?
)
