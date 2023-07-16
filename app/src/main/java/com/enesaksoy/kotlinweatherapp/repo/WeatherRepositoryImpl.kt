package com.enesaksoy.kotlinweatherapp.repo

import com.enesaksoy.kotlinweatherapp.model.WeatherResult
import com.enesaksoy.kotlinweatherapp.service.RetrofitApi
import com.enesaksoy.kotlinweatherapp.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val retrofitApi: RetrofitApi): WeatherRepository {
    override suspend fun getWeather(searchString: String): Resource<WeatherResult> {
        if(searchString.equals("")){
            return Resource.error("Enter the city name!",null)
        }
        return try {
            val response = retrofitApi.getWeather(searchString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error!",null)
            }else{
                Resource.error("No data!",null)
            }
        }catch (e : Exception){
            Resource.error(e.localizedMessage,null)
        }
    }
}