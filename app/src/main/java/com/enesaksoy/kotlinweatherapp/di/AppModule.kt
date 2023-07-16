package com.enesaksoy.kotlinweatherapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enesaksoy.kotlinweatherapp.R
import com.enesaksoy.kotlinweatherapp.repo.WeatherRepository
import com.enesaksoy.kotlinweatherapp.repo.WeatherRepositoryImpl
import com.enesaksoy.kotlinweatherapp.service.RetrofitApi
import com.enesaksoy.kotlinweatherapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun injectApi() : RetrofitApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun injectRepo(retrofitApi: RetrofitApi) : WeatherRepository{
        return WeatherRepositoryImpl(retrofitApi)
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context : Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )
}