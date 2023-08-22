package com.oropeza.ec04_asot.data.repository

import com.oropeza.ec04_asot.data.db.TitanDao
import com.oropeza.ec04_asot.data.response.ApiResponse
import com.oropeza.ec04_asot.data.response.TitanListResponse
import com.oropeza.ec04_asot.data.retrofit.ServiceInstance
import com.oropeza.ec04_asot.model.AttackOnTitan
import kotlinx.coroutines.runBlocking

class TitanRepository(val titanDao: TitanDao? = null) {
    suspend fun getTitans(): ApiResponse<TitanListResponse> {
        return try {
            val response = ServiceInstance.getTitanService().getTitans()
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    suspend fun addToFavorites(titan: AttackOnTitan) {
        titanDao?. let {
            it.addFavorite(titan)
        }
    }

    suspend fun removeToFavorites(titan: AttackOnTitan) {
        titanDao?. let {
            it.removeFavorite(titan)
        }
    }

    fun getFavorite(): List<AttackOnTitan> {
        titanDao?.let {
            return it.getFavorites()
        } ?: kotlin.run {
            return listOf()
        }
    }
}