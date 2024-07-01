package com.example.inventoryappflutterwaveassessment.data.repositories.user

import com.example.inventoryappflutterwaveassessment.data.storage.entities.User

interface UserRepository {

    fun login() : User

    fun getUser(id: String, code:String) : User?

    fun addUser(id: String, code:String)

    fun exists() : Boolean

    fun passwordHash(password: String) : String

}