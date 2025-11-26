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
            val response = ApiClient.client.put("$baseUrl/api/account/recovery") {
                contentType(ContentType.Application.Json)
                setBody(RecoveryRequest(recovery = recovery))
            }
            if (response.status.value == 200) {
                "Recovery Success"
            } else {
                "Error Recovery: ${response.status.value} - ${response.bodyAsText()}"
            }
        } catch (e: Exception) {
            "Failed (Recovery): ${e.message}"
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

    // GET MODE
    suspend fun getMode(): ModeResponse {
        val response = ApiClient.client.get("$baseUrl/api/mode")
        return response.body()
    }

    // POST MODE
    suspend fun updateMode(request: ModeRequest): ModeResponse {
        val response = ApiClient.client.post("$baseUrl/api/mode") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    // POST TRIGGER
    suspend fun triggerRefresh(shouldRefresh: Boolean): String {
        val payload = TriggerRequest(send = shouldRefresh)
        return try {
            val response = ApiClient.client.post("$baseUrl/api/control") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
            if (response.status.value == 200) {
                val apiResponse = response.body<ApiResponse>()
                "Trigger Succeed : ${apiResponse.message}"
            } else {
                "Error Trigger: ${response.status.value} - ${response.bodyAsText()}"
            }
        } catch (e: Exception) {
            "Failed : ${e.message}"
        }
    }

    suspend fun loginPin(pin: String): AuthResponse {
        return try {
            val response = ApiClient.client.post("$baseUrl/api/account/verify/pin") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(pin = pin))
            }
            // Menggunakan body<AuthResponse> karena respons Anda memiliki success & message
            response.body<AuthResponse>()
        } catch (e: Exception) {
            AuthResponse(success = false, message = "Failed (Login): ${e.message}")
        }
    }

    // Fungsi untuk login menggunakan Recovery Code
    suspend fun loginRecovery(code: String): AuthResponse {
        return try {
            val response = ApiClient.client.post("$baseUrl/api/account/verify/recovery") {
                contentType(ContentType.Application.Json)
                setBody(RecoveryLoginRequest(code = code))
            }
            response.body<AuthResponse>()
        } catch (e: Exception) {
            AuthResponse(success = false, message = "Failed (Recovery): ${e.message}")
        }
    }
}