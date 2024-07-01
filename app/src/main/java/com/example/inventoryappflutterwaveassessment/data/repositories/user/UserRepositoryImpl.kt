package com.example.inventoryappflutterwaveassessment.data.repositories.user

import com.example.inventoryappflutterwaveassessment.data.storage.dao.UsersDAO
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val usersDAO: UsersDAO) : UserRepository {
    override fun login(): User {
        return User(0,"","")
    }

    override fun getUser(id: String, code:String): User? {
        return usersDAO.getUser(id, passwordHash(code))
    }

    override fun addUser(id: String, code:String) {
        return usersDAO.insert(User(0, id, passwordHash(code)))
    }

    override fun exists(): Boolean {
        return true
    }

    override fun passwordHash(password: String): String {
        return password
    }

}