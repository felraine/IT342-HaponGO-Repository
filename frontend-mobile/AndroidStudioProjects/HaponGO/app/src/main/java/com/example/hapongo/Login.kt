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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Login : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 9001
    }

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
        val signInGoogle = findViewById<Button>(R.id.google_sign_in_button)

        signInGoogle.setOnClickListener {
            signInWithGoogle()
        }

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


        // OAuth connection part
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))  // important!
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
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

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Welcome, ${user?.displayName}", Toast.LENGTH_SHORT).show()

                    // Go to homepage after successful login
                    startActivity(Intent(this, Homepage::class.java))
                    finish()
                } else {
                    // If sign in fails
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
