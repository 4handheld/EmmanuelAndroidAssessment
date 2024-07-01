package com.example.inventoryappflutterwaveassessment.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User

@Dao
interface ItemsDAO {
    @Query("SELECT * FROM items")
    fun getItemsAll(): List<Items>

    @Insert
    fun insertAll(vararg items: Items)

    @Delete
    fun delete(item: Items)

    @Update
    fun updateItem(item: Items)

}