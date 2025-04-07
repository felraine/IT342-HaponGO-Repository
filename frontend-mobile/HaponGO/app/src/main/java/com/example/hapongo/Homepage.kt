package com.example.hapongo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Homepage : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val topBar: RelativeLayout = findViewById(R.id.topBar) // make sure to set this ID in XML

        ViewCompat.setOnApplyWindowInsetsListener(topBar) { v, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.setPadding(0, topInset, 0, 0) // Add padding to push content below the status bar
            insets
        }

        // Profile click logic
        val imgProfile: ImageView = findViewById(R.id.imgProfile)
        imgProfile.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
        }

        // Drawer logic
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        val menuButton: ImageButton = findViewById(R.id.btnMenu)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_leaderboards -> {
                    // Handle Leaderboards navigation
                }
                R.id.nav_subscription -> {
                    // Handle Subscription navigation
                }
                R.id.nav_dictionary -> {
                    // Handle Dictionary navigation
                }
                R.id.nav_logout -> {
                    // Handle logout logic
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val scrollView = findViewById<ScrollView>(R.id.drawerLayout)

        ViewCompat.setOnApplyWindowInsetsListener(scrollView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                v.paddingTop, // keep existing top padding
                systemBars.right,
                systemBars.bottom // 👈 this is key for bottom spacing
            )
            insets
        }
    }
}
