package com.nidhal.meteoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nidhal.meteoapp.BuildConfig
import com.nidhal.meteoapp.data.model.WeatherResponse
import com.nidhal.meteoapp.data.repository.MainRepository
import com.nidhal.meteoapp.utils.NetworkHelper
import com.nidhal.meteoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var progressCount = MutableLiveData(0)
    var waitingMsg = MutableLiveData(1)
    private val _weather = MutableLiveData<Resource<WeatherResponse>>()
    val weather: LiveData<Resource<WeatherResponse>>
        get() = _weather


     fun fetchWeatherByCity(cityName: String) {
        viewModelScope.launch {
            _weather.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getWeatherByCity(cityName, BuildConfig.API_KEY).let {
                    if (it.isSuccessful) {
                        _weather.postValue(Resource.success(it.body()))
                    } else _weather.postValue(Resource.error(it.raw().message, null))
                }
            } else _weather.postValue(Resource.error("Vous n'êtes pas connecté à internet.", null))
        }
    }
}

