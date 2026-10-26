package com.example.weather.core.network.di

import com.example.weather.core.network.GeocodingApi
import com.example.weather.core.network.OpenMeteoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val FORECAST_BASE_URL = "https://api.open-meteo.com/"
private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"
private const val TIMEOUT_SECONDS = 15L

// У каждого API свой baseUrl, поэтому на каждый — свой Retrofit с квалификатором
private const val FORECAST = "forecast"
private const val GEOCODING = "geocoding"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @Named(FORECAST)
    fun provideForecastRetrofit(client: OkHttpClient, json: Json): Retrofit =
        buildRetrofit(FORECAST_BASE_URL, client, json)

    @Provides
    @Singleton
    @Named(GEOCODING)
    fun provideGeocodingRetrofit(client: OkHttpClient, json: Json): Retrofit =
        buildRetrofit(GEOCODING_BASE_URL, client, json)

    @Provides
    @Singleton
    fun provideOpenMeteoApi(@Named(FORECAST) retrofit: Retrofit): OpenMeteoApi =
        retrofit.create(OpenMeteoApi::class.java)

    @Provides
    @Singleton
    fun provideGeocodingApi(@Named(GEOCODING) retrofit: Retrofit): GeocodingApi =
        retrofit.create(GeocodingApi::class.java)
}

private fun buildRetrofit(baseUrl: String, client: OkHttpClient, json: Json): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
