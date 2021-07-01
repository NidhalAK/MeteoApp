package com.nidhal.meteoapp.data.model

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "weather")
    val weather: List<Weather>?,
    @Json(name = "main")
    val main: Temperature?,
    @Json (name = "wind")
    val wind: Wind?,
    @Json (name = "name")
    var name: String,
    @Json (name = "cod")
    val cod: Int
)
