package com.nidhal.meteoapp.data.api

import com.nidhal.meteoapp.data.model.WeatherResponse
import retrofit2.Response

interface ApiHelper  {
    suspend  fun getWeatherByCity(cityName: String, apiKey: String) : Response<WeatherResponse>
}