package com.dwh.arproject.product.domain.repository

import android.app.Activity
import com.dwh.arproject.product.domain.model.Product

interface ProductRepository {
    fun getProducts() : List<Product>

    fun requestARServiceInstall(activity: Activity) : Boolean
}