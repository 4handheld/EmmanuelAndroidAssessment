package com.example.inventoryappflutterwaveassessment.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Items (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "ownerId") val ownerId: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "qty") val qty: Int?,
    )