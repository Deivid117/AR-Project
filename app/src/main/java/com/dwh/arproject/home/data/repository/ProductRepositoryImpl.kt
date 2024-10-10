package com.dwh.arproject.home.data.repository

import android.app.Activity
import android.util.Log
import com.dwh.arproject.R
import com.dwh.arproject.home.domain.model.ArProductModel
import com.dwh.arproject.home.domain.model.Product
import com.dwh.arproject.home.domain.repository.ProductRepository
import com.google.ar.core.ArCoreApk
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override fun getProducts(): List<Product> {
        return listOf(
            Product(
                name = "crash",
                image = R.drawable.crash,
                price = "$10,000",
                arProductModels = listOf(ArProductModel(name = "crash", image = R.drawable.crash))
            ),
            Product(
                name = "hamburguesa",
                image = R.drawable.hamburguesa,
                price = "$50",
                arProductModels = listOf(ArProductModel(name = "hamburguesa", image = R.drawable.hamburguesa))
            ),
            Product(
                name = "lampara",
                image = R.drawable.lampara1,
                price = "$200",
                arProductModels = listOf(
                    ArProductModel(name = "lampara1", image = R.drawable.lampara1),
                    ArProductModel(name = "lampara2", image = R.drawable.lampara2)
                )
            ),
            Product(
                name = "silla",
                image = R.drawable.silla1,
                price = "$250",
                arProductModels = listOf(
                    ArProductModel(name = "silla1", image = R.drawable.silla1),
                    ArProductModel(name = "silla2", image = R.drawable.silla2),
                    ArProductModel(name = "silla3", image = R.drawable.silla3)
                )
            )
        )
    }

    override fun requestARServiceInstall(activity: Activity): Boolean {
        return  try {
            when (ArCoreApk.getInstance().requestInstall(activity, true)) {
                ArCoreApk.InstallStatus.INSTALLED -> true
                ArCoreApk.InstallStatus.INSTALL_REQUESTED -> false
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            Log.e("ARServiceUnavailable", e.message ?: "Error desconocido")
            false
        } catch (e: Exception) {
            Log.e("ARService", e.message ?: "Error desconocido")
            false
        }
    }
}