package com.hydrosense.corp.data.remote

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("hydrosense_prefs", Context.MODE_PRIVATE)

    fun saveIp(ip: String) {
        prefs.edit().putString("BASE_URL", ip).apply()
    }

    fun getIp(): String {
        return prefs.getString("BASE_URL", "http://192.168.100.92:3000")!!
    }
}
