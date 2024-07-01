package com.example.inventoryappflutterwaveassessment.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventoryappflutterwaveassessment.data.storage.dao.ItemsDAO
import com.example.inventoryappflutterwaveassessment.data.storage.dao.UsersDAO
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User

@Database(entities = [User::class, Items::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){

    abstract fun userDAO(): UsersDAO

    abstract fun itemsDAO(): ItemsDAO

    companion object {
        @JvmStatic val DATABASE_NAME : String = "flutterwavetest.db"
    }

}