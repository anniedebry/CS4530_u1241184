package com.example.assignment4.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class FactResponse(
    val id: String,
    val text: String,
    val source: String? = null,
    val source_url: String? = null,
    val language: String,
    val permalink: String
)

class FactAPI(private val client: HttpClient) {
    suspend fun fetchRandomFact(): FactResponse {
        return client.get("https://uselessfacts.jsph.pl/random.json?language=en").body()
    }
}

fun createKtorClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}