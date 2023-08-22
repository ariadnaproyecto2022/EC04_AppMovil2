package com.oropeza.ec04_asot.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceInstance {
    // https://api.attackontitanapi.com/characters
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.attackontitanapi.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getTitanService(): TitanService = retrofit.create(TitanService::class.java)
}