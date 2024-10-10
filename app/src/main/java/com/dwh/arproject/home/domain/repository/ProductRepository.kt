package com.dwh.arproject.home.domain.repository

import android.app.Activity
import com.dwh.arproject.home.domain.model.Product

interface ProductRepository {
    fun getProducts() : List<Product>

    fun requestARServiceInstall(activity: Activity) : Boolean
}