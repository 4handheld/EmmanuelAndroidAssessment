package com.example.inventoryappflutterwaveassessment.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User

@Dao
interface UsersDAO {

    @Query("SELECT * FROM user")
    fun getUsersAll(): List<User>

    @Query("SELECT * FROM user WHERE uid == :userId")
    fun getUserById(userId: Int): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM user where email = :id and password = :code")
    fun getUser(id: String, code:String): User?

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

}