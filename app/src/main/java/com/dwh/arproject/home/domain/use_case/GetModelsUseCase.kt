package com.dwh.arproject.home.domain.use_case

import com.dwh.arproject.home.domain.repository.ProductRepository
import javax.inject.Inject

class GetModelsUseCase@Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.getProducts()
}