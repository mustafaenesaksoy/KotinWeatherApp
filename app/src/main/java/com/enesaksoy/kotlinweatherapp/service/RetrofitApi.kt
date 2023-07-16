package com.enesaksoy.kotlinweatherapp.service

import com.enesaksoy.kotlinweatherapp.model.WeatherResult
import com.enesaksoy.kotlinweatherapp.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("data/2.5/weather?")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey : String = API_KEY
    ) : Response<WeatherResult>
}