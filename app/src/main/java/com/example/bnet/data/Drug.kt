package com.example.bnet.data

data class LightDrug(
    val id: Int,
    val image: String?,
    val name: String,
    val description: String
)

data class FullDrug(
    val id: Int,
    val image: String,
    val categories: Category,
    val name: String,
    val description: String,
)

data class Category(
    val id: Int,
    val icon: String,
    val image: String?,
    val name: String
)
