package com.hydrosense.corp.data.remote
import com.hydrosense.corp.data.remote.Prefs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context

object RetrofitInstance {

    private var retrofit: Retrofit? = null

    fun getApi(context: Context): SensorApi {
        val baseUrl = Prefs(context).getIp()
        if (retrofit == null || retrofit?.baseUrl().toString() != baseUrl) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(SensorApi::class.java)
    }

}

