package com.dwh.arproject.product.domain.model

import kotlinx.uuid.UUID
import kotlinx.uuid.generateUUID
import kotlin.random.Random

data class ArProductModel(
    val id: String = UUID.generateUUID(Random).toString(),
    val name: String = "",
    val image: Int = 0,
)