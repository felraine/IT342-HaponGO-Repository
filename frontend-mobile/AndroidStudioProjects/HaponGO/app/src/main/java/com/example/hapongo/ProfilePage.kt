package com.example.hapongo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hapongo.model.User


class ProfilePage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profilepage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val name = findViewById<EditText>(R.id.nameEditText)
        val userEmail = findViewById<EditText>(R.id.emailEditText)

        name.isEnabled = false
        userEmail.isEnabled = false

        // Example user data (replace with actual user from login/session)
        val currentUser = intent.getSerializableExtra("user") as? User

        if (currentUser != null) {
            name.setText(currentUser.name)
            userEmail.setText(currentUser.email)
        } else {
            name.setText("Unknown")
            userEmail.setText("Unknown")
        }


        val editButton = findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            val intent = Intent(this, ProfilePageEdit::class.java)
            intent.putExtra("user", currentUser)
            startActivity(intent)
        }
    }
}