package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryItem (
    val uid: Int,
    val ownerId: Int?,
    val name: String?,
    val description: String?,
    val price: Double?,
    val qty: Int?,
) : Parcelable