package com.example.geofencing_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.geofencing_project.database.users.UserRepository
import com.example.geofencing_project.database.users.UserRoomDatabase
import com.example.geofencing_project.database.users.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = UserViewModel(UserRepository(UserRoomDatabase.getDatabase(this).userDao()))

        // Username and password fields
        val usernameField = findViewById<EditText>(R.id.et_username)
        val passwordField = findViewById<EditText>(R.id.et_password)

        // Log In button click handler
        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            // Validate the user in the background
            lifecycleScope.launch {
                validateUser(username, password)
            }
        }

        // Sign Up button handler
        val signUpButton = findViewById<Button>(R.id.btn_signup)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to validate user credentials and navigate
    private suspend fun validateUser(username: String, password: String) {
        withContext(Dispatchers.IO) {
            val user = userViewModel.getUserByUsername(username)

            withContext(Dispatchers.Main) {
                if (user == null) {
                    // Username is invalid
                    Toast.makeText(this@MainActivity, "Invalid username!", Toast.LENGTH_SHORT).show()
                } else if (user.password != password) {
                    // Password is incorrect
                    Toast.makeText(this@MainActivity, "Invalid password!", Toast.LENGTH_SHORT).show()
                } else {
                    // Username and password are correct, navigate based on role
                    when (user.role) {
                        "admin" -> {
                            val intent = Intent(this@MainActivity, AdminActivity::class.java).apply {
                                putExtra("USERNAME", user.name) // Pass username to AdminActivity
                            }
                            startActivity(intent)
                        }
                        "user" -> {
                            val intent = Intent(this@MainActivity, UserActivity::class.java).apply {
                                putExtra("USERNAME", user.name) // Pass username to UserActivity
                            }
                            startActivity(intent)
                        }
                        else -> {
                            Toast.makeText(this@MainActivity, "Invalid role!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            }
        }
}