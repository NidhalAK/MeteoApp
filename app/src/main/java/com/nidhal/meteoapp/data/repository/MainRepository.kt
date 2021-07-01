package com.nidhal.meteoapp.data.repository

import com.nidhal.meteoapp.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper){

    suspend fun getWeatherByCity(cityName: String, apiKey: String) =  apiHelper.getWeatherByCity(cityName, apiKey)
}