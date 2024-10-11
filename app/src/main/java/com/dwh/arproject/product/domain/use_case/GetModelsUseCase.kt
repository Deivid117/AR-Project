package com.dwh.arproject.product.domain.use_case

import com.dwh.arproject.product.domain.repository.ProductRepository
import javax.inject.Inject

class GetModelsUseCase@Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.getProducts()
}