package com.example.geofencing_project.database.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE userid = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM users WHERE name = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE role = 'admin'")
    suspend fun getAllAdmins(): List<User>

    @Query("SELECT * FROM users WHERE userid = :userId AND role = :role")
    fun getUserByIdAndRole(userId: Int, role: String): LiveData<User>


    @Query("SELECT * FROM users WHERE role = 'employee'")
    suspend fun getAllEmployees(): List<User>
}
