package com.dwh.arproject.home.domain.use_case

import android.app.Activity
import com.dwh.arproject.home.domain.repository.ProductRepository
import javax.inject.Inject

class RequestARServiceInstallUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(activity: Activity): Boolean = productRepository.requestARServiceInstall(activity)
}