package com.enesaksoy.kotlinweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.kotlinweatherapp.model.WeatherResult
import com.enesaksoy.kotlinweatherapp.repo.WeatherRepository
import com.enesaksoy.kotlinweatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(private val repo : WeatherRepository): ViewModel() {

    private val getWeather = MutableLiveData<Resource<WeatherResult>>()
    val getWeatherData : LiveData<Resource<WeatherResult>>
    get() = getWeather

    fun getWeather(searchString : String){
        getWeather.value = Resource.loading(null)
        viewModelScope.launch {
            getWeather.value = repo.getWeather(searchString)
        }
    }
}