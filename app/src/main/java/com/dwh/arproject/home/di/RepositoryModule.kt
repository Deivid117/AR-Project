package com.dwh.arproject.home.di

import com.dwh.arproject.home.data.repository.ProductRepositoryImpl
import com.dwh.arproject.home.domain.repository.ProductRepository
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