package com.example.hapongo

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hapongo.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LessonContentActivity : AppCompatActivity() {

    private lateinit var lessonContentText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_lesson_content)

        lessonContentText = findViewById(R.id.lessonContentText)

        val lessonId = intent.getLongExtra("LESSON_ID", -1L)

        if (lessonId != -1L) {
            fetchLessonContent(lessonId)
        } else {
            Toast.makeText(this, "Invalid Lesson ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchLessonContent(lessonId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getLessonContentsByLessonId(lessonId)

                if (response.isSuccessful) {
                    val lessonContents = response.body()
                    if (lessonContents.isNullOrEmpty()) {
                        runOnUiThread {
                            lessonContentText.text = "No content available."
                        }
                    } else {
                        runOnUiThread {
                            // Initialize a StringBuilder to accumulate all the lesson content
                            val contentBuilder = StringBuilder()

                            // Loop through all lesson contents and append each to the StringBuilder
                            for (lesson in lessonContents) {
                                contentBuilder.append("""
                                Japanese Word: ${lesson.japaneseWord}
                                Pronunciation: ${lesson.pronunciation}
                                English Word: ${lesson.englishWord}

                            """.trimIndent())
                            }

                            // Set the accumulated content to the TextView
                            lessonContentText.text = contentBuilder.toString()
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
