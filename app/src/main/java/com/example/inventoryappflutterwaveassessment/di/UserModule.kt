package com.example.inventoryappflutterwaveassessment.di

import android.content.Context
import androidx.room.Room
import com.example.inventoryappflutterwaveassessment.data.repositories.user.UserRepository
import com.example.inventoryappflutterwaveassessment.data.repositories.user.UserRepositoryImpl
import com.example.inventoryappflutterwaveassessment.data.storage.Database
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun providesUserRepository(impl: UserRepositoryImpl): UserRepository

}