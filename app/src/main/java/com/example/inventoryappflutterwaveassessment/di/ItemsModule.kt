package com.example.inventoryappflutterwaveassessment.di

import com.example.inventoryappflutterwaveassessment.data.repositories.items.ItemsRepository
import com.example.inventoryappflutterwaveassessment.data.repositories.items.ItemsRepositoryImpl
import com.example.inventoryappflutterwaveassessment.data.repositories.user.UserRepository
import com.example.inventoryappflutterwaveassessment.data.repositories.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ItemsModule {

    @Binds
    abstract fun providesItemsRepository(impl: ItemsRepositoryImpl): ItemsRepository


}