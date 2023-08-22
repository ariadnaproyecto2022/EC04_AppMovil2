package com.oropeza.ec04_asot.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TitanFirebase(
    val img: String,
    val name: String,
    val gender: String,
    val birthplace: String,
    val status: String,
    val occupation: String
) : Parcelable