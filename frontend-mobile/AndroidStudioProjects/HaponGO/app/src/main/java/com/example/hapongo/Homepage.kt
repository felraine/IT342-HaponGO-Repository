package com.example.hapongo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hapongo.model.Lesson
import com.example.hapongo.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.hapongo.network.RetrofitInstance

class Homepage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var lessonAdapter: LessonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)

        // Set up window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homepage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView for lessons
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lessonAdapter = LessonAdapter { lesson -> openLessonContent(lesson.lessonId) }
        recyclerView.adapter = lessonAdapter

        // Fetch lessons from API
        fetchLessons()

        // Profile image click listener (as before)
        val imgProfile: ImageView = findViewById(R.id.imgProfile)
        imgProfile.setOnClickListener {
            // Open profile page (existing logic)
        }
    }

    private fun fetchLessons() {
        val apiService = RetrofitInstance.api

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getAllLessons()

                if (response.isSuccessful) {
                    val lessons = response.body() ?: emptyList()
                    runOnUiThread {
                        lessonAdapter.submitList(lessons)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@Homepage, "Failed to load lessons", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Homepage, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openLessonContent(lessonId: Long) {
        val intent = Intent(this, LessonContentActivity::class.java)
        intent.putExtra("LESSON_ID", lessonId)
        startActivity(intent)
    }
}
