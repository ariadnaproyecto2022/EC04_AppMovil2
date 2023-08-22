package com.oropeza.ec04_asot.data.repository

import com.google.firebase.firestore.FirebaseFirestore

class TitanDBRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val titansCollection = firestore.collection("titan")

}