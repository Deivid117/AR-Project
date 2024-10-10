package com.dwh.arproject.home.domain.model

import com.dwh.arproject.R
import kotlinx.uuid.*
import kotlin.random.Random

data class Product(
    val id: String = UUID.generateUUID(Random).toString(),
    val name: String = "",
    val image: Int = R.drawable.ic_image,
    val price: String = "",
    val arProductModels: List<ArProductModel> = listOf()
)
