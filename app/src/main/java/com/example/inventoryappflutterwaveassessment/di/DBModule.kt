package com.example.inventoryappflutterwaveassessment.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.inventoryappflutterwaveassessment.data.storage.Database
import com.example.inventoryappflutterwaveassessment.data.storage.dao.ItemsDAO
import com.example.inventoryappflutterwaveassessment.data.storage.dao.UsersDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            Database::DATABASE_NAME.toString()
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPref(@ApplicationContext appContext: Context): SharedPreferences {
        val masterKey: MasterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            appContext,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun usersDAO(db: Database): UsersDAO = db.userDAO()

    @Provides
    @Singleton
    fun itemsDAO(db: Database): ItemsDAO = db.itemsDAO()

}