package com.example.geofencing_project.database.users

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getAllAdmins(): List<User> {
        return userDao.getAllAdmins()
    }

    suspend fun getAllEmployees(): List<User> {
        return userDao.getAllEmployees()
    }

    fun getUserByIdAndRole(userId: Int, role: String): LiveData<User> {
        return userDao.getUserByIdAndRole(userId, role)
    }


}
