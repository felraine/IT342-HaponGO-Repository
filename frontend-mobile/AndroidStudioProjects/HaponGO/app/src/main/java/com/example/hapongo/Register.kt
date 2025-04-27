package com.example.hapongo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hapongo.model.User
import com.example.hapongo.network.ApiService
import com.example.hapongo.network.RetrofitInstance
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val signUpTextView = findViewById<TextView>(R.id.login_account_text)
        val signUpEmail = findViewById<TextInputEditText>(R.id.email_input_signup)
        val signUpPassword = findViewById<TextInputEditText>(R.id.password_input_signup)
        val confirmPassword = findViewById<TextInputEditText>(R.id.confirm_password_input)
        val signUpButton = findViewById<Button>(R.id.sign_up_button)

        apiService = RetrofitInstance.api

        signUpTextView.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val email = signUpEmail.text.toString().trim()
            val password = signUpPassword.text.toString().trim()
            val confirm = confirmPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        val user = User(
            email = email,
            password = password,
            name = email.substringBefore("@"), // basic name fallback
            is_admin = 0,
            subscription_status = 0
        )

        lifecycleScope.launch {
            try {
                val response = apiService.registerUser(user)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@Register, "Registration successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Register, Login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@Register, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@Register, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}