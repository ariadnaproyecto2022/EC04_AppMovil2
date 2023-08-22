package com.oropeza.ec04_asot.view.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oropeza.ec04_asot.data.repository.TitanRepository
import com.oropeza.ec04_asot.data.response.ApiResponse
import com.oropeza.ec04_asot.model.AttackOnTitan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitanListViewModel: ViewModel() {
    val repository = TitanRepository()
    val titanList: MutableLiveData<List<AttackOnTitan>> = MutableLiveData<List<AttackOnTitan>>()

    fun getTitansFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTitans()
            when (response) {
                is ApiResponse.Error -> {
                    // colocar error
                }
                is ApiResponse.Success -> {
                    titanList.postValue(response.data.results)
                }
            }
        }
    }
}