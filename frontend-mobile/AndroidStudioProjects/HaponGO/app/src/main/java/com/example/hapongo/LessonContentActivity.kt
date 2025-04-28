package com.example.hapongo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hapongo.adapter.LessonContentAdapter
import com.example.hapongo.model.Lesson
import com.example.hapongo.model.LessonQuizResponse
import com.example.hapongo.model.User
import com.example.hapongo.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LessonContentActivity : AppCompatActivity() {

    private lateinit var lessonRecyclerView: RecyclerView
    private lateinit var lessonContentAdapter: LessonContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_content)

        lessonRecyclerView = findViewById(R.id.lessonRecyclerView)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        lessonContentAdapter = LessonContentAdapter(emptyList())

        lessonRecyclerView.layoutManager = LinearLayoutManager(this)
        lessonRecyclerView.adapter = lessonContentAdapter

        btnBack.setOnClickListener {
            finish()
        }

        val lessonId = intent.getLongExtra("LESSON_ID", -1L)
        if (lessonId != -1L) {
            fetchLessonContent(lessonId)
        } else {
            Toast.makeText(this, "Invalid Lesson ID", Toast.LENGTH_SHORT).show()
        }

        val quizBtn = findViewById<Button>(R.id.btnAnswerQuiz)
        quizBtn.setOnClickListener {
            if (lessonId != -1L) {
                val intent = Intent(this, LessonQuizActivity::class.java)
                intent.putExtra("LESSON_ID", lessonId) // Pass only the lessonId
                startActivity(intent)
            } else {
                Toast.makeText(this, "No lesson ID provided", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchLessonContent(lessonId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getLessonContentsByLessonId(lessonId)

                if (response.isSuccessful) {
                    val lessonContents = response.body()
                    if (!lessonContents.isNullOrEmpty()) {
                        runOnUiThread {
                            lessonContentAdapter.updateData(lessonContents)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@LessonContentActivity, "No lesson content available", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LessonContentActivity, "Failed to load lesson content", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@LessonContentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}