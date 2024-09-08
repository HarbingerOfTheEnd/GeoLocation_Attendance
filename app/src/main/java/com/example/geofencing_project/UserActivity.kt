package com.example.geofencing_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.geofencing_project.attendance.AttendanceViewModel
import com.example.geofencing_project.database.users.User
import com.example.geofencing_project.database.users.UserViewModel
import kotlinx.coroutines.*

class UserActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val attendanceViewModel: AttendanceViewModel by viewModels()

    private lateinit var userIdTextView: TextView
    private lateinit var locationNameTextView: TextView
    private lateinit var statusTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Initialize UI elements
        userIdTextView = findViewById(R.id.user_id_textview)
        locationNameTextView = findViewById(R.id.location_name_textview)
        statusTextView = findViewById(R.id.status_textview)

        // Get the username from the Intent
        val username = intent.getStringExtra("USERNAME")

        if (username != null) {
            // Fetch the user details based on the username
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val user: User? = userViewModel.getUserByUsername(username)
                    if (user != null) {
                        Log.d("UserActivity", "User fetched: ${user.userid}")
                        displayUserDetails(user)
                        fetchAndDisplayAttendanceData(user.userid)
                    } else {
                        Toast.makeText(this@UserActivity, "Error: User not found!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("UserActivity", "Error fetching user: ${e.message}")
                }
            }
        } else {
            Toast.makeText(this, "Error: Username not found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayUserDetails(user: User) {
        if (user.userid != null) {
            userIdTextView.text = "User ID: ${user.userid}"
        } else {
            Log.e("UserActivity", "User ID is null")
            userIdTextView.text = "User ID: Not Available"
        }
    }

    private fun fetchAndDisplayAttendanceData(userId: Int) {
        attendanceViewModel.allAttendances.observe(this, Observer { attendances ->
            val attendance = attendances.find { it.user_id == userId }
            if (attendance != null) {
                locationNameTextView.text = "Location Name: ${attendance.location_id}"
                statusTextView.text = "Status: ${attendance.status}"
            } else {
                Toast.makeText(this, "Attendance details not found", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
