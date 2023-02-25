package com.example.bnet.data.network

import com.example.bnet.data.FullDrug
import com.example.bnet.data.LightDrug
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("index/?limit=6")
    suspend fun getDrugsList(@Query("offset") offset: Int): Response<List<LightDrug>>

    @GET("item")
    suspend fun getDrug(@Query("id") id: Int): Response<FullDrug>

    @GET("index/?limit=6")
    suspend fun searchDrugs(
        @Query("search") searchRequest: String,
        @Query("offset") offset: Int
    ): Response<List<LightDrug>>

    companion object {
        private const val BASE_URL = "http://shans.d2.i-partner.ru/api/ppp/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)

        }

    }
}