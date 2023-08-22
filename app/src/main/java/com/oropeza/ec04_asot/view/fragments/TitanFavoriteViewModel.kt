package com.oropeza.ec04_asot.view.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oropeza.ec04_asot.data.db.TitanDataBase
import com.oropeza.ec04_asot.data.repository.TitanRepository
import com.oropeza.ec04_asot.model.AttackOnTitan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitanFavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TitanRepository
    private val _favorites: MutableLiveData<List<AttackOnTitan>> = MutableLiveData()
    val favorites: LiveData<List<AttackOnTitan>> = _favorites
    init {
        val db = TitanDataBase.getDatabase(application)
        repository = TitanRepository(db.titanDao())
    }
    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getFavorite()
            _favorites.postValue(data)
        }
    }
}