package com.example.weather.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val TIMEOUT_MS = 15_000L

/** Один HttpClient на оба API: у них разные адреса, поэтому URL задан в самих API-классах. */
fun createHttpClient(json: Json): HttpClient =
    // Движок (OkHttp на Android, Darwin на iOS) выбирается автоматически по зависимостям платформы
    HttpClient {
        // Ответы с кодом не 2xx превращаются в исключение, которое репозиторий оборачивает в Result
        expectSuccess = true
        install(ContentNegotiation) { json(json) }
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT_MS
            socketTimeoutMillis = TIMEOUT_MS
            requestTimeoutMillis = TIMEOUT_MS * 2
        }
    }

val networkModule = module {
    single { Json { ignoreUnknownKeys = true } }
    single { createHttpClient(get()) }
    singleOf(::OpenMeteoApi)
    singleOf(::GeocodingApi)
}
