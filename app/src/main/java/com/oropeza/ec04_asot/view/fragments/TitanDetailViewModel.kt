package com.oropeza.ec04_asot.view.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oropeza.ec04_asot.data.db.TitanDataBase
import com.oropeza.ec04_asot.data.repository.TitanRepository
import com.oropeza.ec04_asot.model.AttackOnTitan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitanDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TitanRepository
    init {
        val db = TitanDataBase.getDatabase(application)
        repository = TitanRepository(db.titanDao())
    }

    fun addFavorite(titan: AttackOnTitan) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(titan)
        }
    }

    fun removeFavorite(titan: AttackOnTitan) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeToFavorites(titan)
        }
    }
}