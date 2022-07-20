package com.gyunni.trackbox

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var service : DeliveryService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.tracker.delivery/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(DeliveryService::class.java)
    }
}