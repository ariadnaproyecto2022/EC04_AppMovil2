package com.oropeza.ec04_asot.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "titan")
@Parcelize
data class AttackOnTitan (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("img")
    val img: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("age")
    val age: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("birthplace")
    val birthplace: String,
    @SerializedName("residence")
    val residence: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("occupation")
    val occupation: String,
    var isFavorite: Boolean = false
) : Parcelable