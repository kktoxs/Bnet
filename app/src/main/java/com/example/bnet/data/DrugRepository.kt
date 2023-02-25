package com.example.bnet.data

import com.example.bnet.data.network.ApiService

class DrugRepository() {
    private val apiService =  ApiService.create()

    suspend fun getDrugs(offset:Int) = apiService.getDrugsList(offset)

    suspend fun getDrugById(id: Int) = apiService.getDrug(id)

    suspend fun searchDrugs(search: String, offset: Int) = apiService.searchDrugs(search, offset)
}