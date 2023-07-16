package com.enesaksoy.kotlinweatherapp.repo

import com.enesaksoy.kotlinweatherapp.model.WeatherResult
import com.enesaksoy.kotlinweatherapp.util.Resource

interface WeatherRepository {

    suspend fun getWeather(searchString : String): Resource<WeatherResult>
}