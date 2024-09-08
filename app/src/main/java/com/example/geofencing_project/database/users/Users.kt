package com.example.geofencing_project.database.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userid: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val phone_no: String,
    val role: String  // "admin" or "employee"
) {


}
