package com.hydrosense.corp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import com.hydrosense.corp.domain.model.*
import io.ktor.client.call.body


object ApiClient {
    val client = HttpClient(Android) {
        // Setup Content Negotiation untuk JSON
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }
}

class ApiService(private val baseUrl: String) {

    // Fungsi untuk mengirim PIN
    suspend fun updatePin(pin: String): String {
        return try {
            val response = ApiClient.client.post("$baseUrl/api/account") {
                contentType(ContentType.Application.Json)
                setBody(PinRequest(pin = pin))
            }
            if (response.status.value == 200) {
                // response.body<ApiResponse>().message // Gunakan jika response konsisten
                "PIN Berhasil Diperbarui!"
            } else {
                "Error PIN: ${response.status.value} - ${response.bodyAsText()}"
            }
        } catch (e: Exception) {
            "Koneksi Gagal (PIN): ${e.message}"
        }
    }

    // Fungsi untuk mengirim Recovery Code
    suspend fun updateRecovery(recovery: String): String {
        return try {
            val response = ApiClient.client.post("$baseUrl/api/account/recovery") {
                contentType(ContentType.Application.Json)
                setBody(RecoveryRequest(recovery = recovery))
            }
            if (response.status.value == 200) {
                "Recovery Berhasil Diperbarui!"
            } else {
                "Error Recovery: ${response.status.value} - ${response.bodyAsText()}"
            }
        } catch (e: Exception) {
            "Koneksi Gagal (Recovery): ${e.message}"
        }
    }

    // GET /api/data
    suspend fun getSensorData(): List<SensorData> {
        return try {
            ApiClient.client.get("$baseUrl/api/data/latest") {
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            emptyList() // biar aman kalau error
        }
    }

    /** GET /mode */
    suspend fun getMode(): ModeResponse {
        val response = ApiClient.client.get("$baseUrl/api/mode")
        return response.body()
    }

    /** POST /mode */
    suspend fun updateMode(request: ModeRequest): ModeResponse {
        val response = ApiClient.client.post("$baseUrl/api/mode") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
}