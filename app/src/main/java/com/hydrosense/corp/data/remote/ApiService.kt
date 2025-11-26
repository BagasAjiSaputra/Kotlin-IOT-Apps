package com.hydrosense.corp.data.remote

import com.hydrosense.corp.domain.model.*
import retrofit2.http.GET

interface SensorApi {
    @GET("api/data/latest")
    suspend fun getSensorHistory(): List<TestModel>

}
