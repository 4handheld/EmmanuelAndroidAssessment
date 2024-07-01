package com.example.inventoryappflutterwaveassessment.data.repositories.items

import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items

interface ItemsRepository {


    fun getItems(): List<Items>

    fun getItemsByUserID(): List<Items>

    fun deleteItem(items: Items)

    fun updateItem(items: Items)

    fun addItem(items: Items)

}