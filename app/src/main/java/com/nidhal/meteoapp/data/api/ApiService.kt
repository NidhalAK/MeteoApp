package com.nidhal.meteoapp.data.api

import com.nidhal.meteoapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //get weather by city name
    @GET("weather")
    suspend  fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

}