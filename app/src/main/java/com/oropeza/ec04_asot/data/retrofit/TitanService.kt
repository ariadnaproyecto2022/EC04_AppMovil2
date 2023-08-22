package com.oropeza.ec04_asot.data.retrofit

import com.oropeza.ec04_asot.data.response.TitanListResponse
import retrofit2.http.GET

interface TitanService {
    @GET("characters")
    suspend fun getTitans() : TitanListResponse
}