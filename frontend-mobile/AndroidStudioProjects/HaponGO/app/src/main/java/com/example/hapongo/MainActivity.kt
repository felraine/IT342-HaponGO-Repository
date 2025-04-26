package com.example.hapongo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hapongo.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import com.example.hapongo.model.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)

        button1.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }

        val viewModel: UserViewModel by viewModels()
        val fetchButton = findViewById<Button>(R.id.button4)
        val usersContainer = findViewById<LinearLayout>(R.id.usersContainer)

        fetchButton.setOnClickListener {
            viewModel.loadUsers()
        }

        lifecycleScope.launch {
            viewModel.users.collect { list ->
                // Clear any old views
                usersContainer.removeAllViews()
                // For each user, add a TextView
                list.forEach { user ->
                    usersContainer.addView(createUserTextView(user))
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { msg ->
                msg?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun createUserTextView(user: User): TextView {
        return TextView(this).apply {
            text = "ID: ${user.userId}   Name: ${user.name}   Email: ${user.email}"
            setPadding(16, 16, 16, 16)
        }
    }
}