package com.makinul.bs23104.di

import com.makinul.bs23104.data.repository.ProductRepository
import com.makinul.bs23104.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}