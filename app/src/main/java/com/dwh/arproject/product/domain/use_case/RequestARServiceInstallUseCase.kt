package com.dwh.arproject.product.domain.use_case

import android.app.Activity
import com.dwh.arproject.product.domain.repository.ProductRepository
import javax.inject.Inject

class RequestARServiceInstallUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(activity: Activity): Boolean = productRepository.requestARServiceInstall(activity)
}