package com.nidhal.meteoapp.data.api

import com.nidhal.meteoapp.data.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl  @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getWeatherByCity(
        cityName: String,
        apiKey: String
    ): Response<WeatherResponse> = apiService.getWeatherByCity(cityName, apiKey)
}