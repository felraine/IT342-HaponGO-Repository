package com.example.hapongo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hapongo.model.User
import com.example.hapongo.network.ApiService
import kotlinx.coroutines.launch
import com.example.hapongo.network.RetrofitInstance

class ProfilePageEdit : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private var currentUser: User? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_edit)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editprofilepage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiService = RetrofitInstance.api
        currentUser = intent.getSerializableExtra("user") as? User

        val backButton: ImageButton = findViewById(R.id.backButton)
        val nameEditText: EditText = findViewById(R.id.nameEditText2)
        val emailEditText: EditText = findViewById(R.id.emailEditText2)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText2)
        val confirmPasswordEditText: EditText = findViewById(R.id.confirmPasswordEditText)
        val saveButton: Button = findViewById(R.id.saveButton2)


        val cancel = findViewById<Button>(R.id.cancelButton)
        cancel.setOnClickListener {
            finish()
        }

        emailEditText.isEnabled = false

        if (currentUser != null) {
            nameEditText.setText(currentUser!!.name)
            emailEditText.setText(currentUser!!.email)
        } else {
            nameEditText.setText("")
            emailEditText.setText("")
            passwordEditText.setText("")
            confirmPasswordEditText.setText("")
        }

        // Back button logic
        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val updatedName = nameEditText.text.toString().trim()
            val updatedPassword = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Simple validation
            if (updatedName.isEmpty() || updatedPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (updatedPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateUserProfile(updatedName, updatedPassword)
        }
    }

    private fun updateUserProfile(updatedName: String, updatedPassword: String) {
        val user = currentUser
        if (user != null) {
            val updatedUser = user.copy(
                name = updatedName,
                password = updatedPassword
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.api.updateUser(user.userId?.toLong() ?: 0, updatedUser)
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(this@ProfilePageEdit, "Profile updated!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@ProfilePageEdit, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ProfilePageEdit, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
