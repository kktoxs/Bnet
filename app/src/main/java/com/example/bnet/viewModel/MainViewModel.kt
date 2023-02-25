package com.example.bnet.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bnet.data.FullDrug
import com.example.bnet.data.DrugRepository
import com.example.bnet.data.LightDrug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    val drugsList = MutableLiveData<List<LightDrug>>()
    val currentDrug = MutableLiveData<FullDrug?>()

    private val repository = DrugRepository()

    fun getDrugs(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDrugs(offset)
            drugsList.postValue(response.body())
        }
    }

    fun getDrugById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDrugById(id)
            currentDrug.postValue(response.body())
        }
    }

    fun getDrugsBySearch(search: String, offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.searchDrugs(search, offset)
            drugsList.postValue(response.body())
        }
    }

    fun closeCurrDrug() {
        currentDrug.postValue(null)
    }
}
