package com.example.inventoryappflutterwaveassessment.data.repositories.user

import com.example.inventoryappflutterwaveassessment.data.storage.dao.UsersDAO
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(private val usersDAO: UsersDAO) : UserRepository {
    override fun login(): User {
        return User(0,"","")
    }

    override fun getUser(id: String, code:String): User? {
        return usersDAO.getUser(id, passwordHash(code))
    }

    override fun addUser(id: String, code:String) {
        return usersDAO.insert(User(null, id, passwordHash(code)))
    }

    override fun exists(): Boolean {
        return true
    }

    override fun passwordHash(password: String): String {
        return getSha256Hash(password)!!
    }

    private fun getSha256Hash(password: String): String? {
        return try {
            var digest: MessageDigest? = null
            try {
                digest = MessageDigest.getInstance("SHA-256")
            } catch (e1: NoSuchAlgorithmException) {
                e1.printStackTrace()
            }
            digest!!.reset()
            bin2hex(digest.digest(password.toByteArray()))
        } catch (ignored: Exception) {
            password
        }
    }

    private fun bin2hex(data: ByteArray): String {
        val hex = StringBuilder(data.size * 2)
        for (b in data) hex.append(String.format("%02x", b.toInt() and 0xFF))
        return hex.toString()
    }

}