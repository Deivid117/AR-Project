package com.dwh.arproject.product.di

import com.dwh.arproject.product.data.repository.ProductRepositoryImpl
import com.dwh.arproject.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl) : ProductRepository
}