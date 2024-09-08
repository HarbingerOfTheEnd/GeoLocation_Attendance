package com.example.geofencing_project.database.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
//import com.example.geofencing_project.data.User  // Correctly import your User data class
import com.example.geofencing_project.database.users.UserRepository // Import your repository

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // Insert user into the database
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
    fun getAllUsers(): LiveData<List<User>> {
        return repository.getAllUsers()
    }
    // Get a user by their ID and role
    fun getUserByIdAndRole(userId: Int, role: String): LiveData<User> {
        return repository.getUserByIdAndRole(userId, role)
    }



    // Get user by their username (suspending function)
    suspend fun getUserByUsername(username: String): User? {
        return repository.getUserByUsername(username)
    }

    // Fetch all admins (asynchronous)
    fun getAllAdmins() = viewModelScope.launch {
        repository.getAllAdmins()
    }

    // Fetch all employees (asynchronous)
    fun getAllEmployees() = viewModelScope.launch {
        repository.getAllEmployees()
    }
}
