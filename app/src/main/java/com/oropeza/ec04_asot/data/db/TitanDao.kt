package com.oropeza.ec04_asot.data.db

import androidx.room.*
import com.oropeza.ec04_asot.model.AttackOnTitan

@Dao
interface TitanDao {
    @Query("SELECT * FROM titan")
    fun getFavorites(): List<AttackOnTitan>

    @Delete
    suspend fun removeFavorite(titan: AttackOnTitan)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(titan: AttackOnTitan)

}