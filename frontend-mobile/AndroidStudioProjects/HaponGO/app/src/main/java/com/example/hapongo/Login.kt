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
import com.example.hapongo.network.ApiService
import com.example.hapongo.network.RetrofitInstance
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiService = RetrofitInstance.api

        // ← here are your corrected view bindings:
        val emailInput = findViewById<TextInputEditText>(R.id.email_input_signup)
        val passwordInput = findViewById<TextInputEditText>(R.id.password_input_signup)
        val signinButton  = findViewById<Button>(R.id.sign_in_button)
        val signUpTextView = findViewById<TextView>(R.id.create_account_text)

        signUpTextView.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        signinButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.loginUser(email, password)
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    Toast.makeText(this@Login, "Welcome, ${user.name}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@Login, Homepage::class.java)
                    intent.putExtra("user", user) // Pass user as Serializable
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@Login, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@Login, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
