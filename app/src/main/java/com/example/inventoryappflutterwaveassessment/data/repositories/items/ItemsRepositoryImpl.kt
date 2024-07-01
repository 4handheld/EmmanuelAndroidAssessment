package com.example.inventoryappflutterwaveassessment.data.repositories.items

import com.example.inventoryappflutterwaveassessment.data.storage.dao.ItemsDAO
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(private val itemsDAO: ItemsDAO):ItemsRepository {
    override fun getItems(): List<Items> = itemsDAO.getItemsAll()

    override fun getItemsByUserID(userId: Int): List<Items> = itemsDAO.getUserItemsAll(userId)

    override fun deleteItem(item: Items) = itemsDAO.delete(item)
    override fun updateItem(items: Items)  = itemsDAO.updateItem(items)

    override fun addItem(items: Items) = itemsDAO.insertAll(items)

    override fun nameExists(name: String) = itemsDAO.nameExists(name)
}