package com.example.hapongo

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hapongo.model.Lesson
import com.example.hapongo.model.LessonQuiz
import com.example.hapongo.model.LessonQuizResponse
import com.example.hapongo.network.ApiService
import com.example.hapongo.network.RetrofitInstance
import kotlinx.coroutines.launch

class LessonQuizActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var choiceButtons: List<Button>
    private lateinit var resultText: TextView
    private lateinit var nextButton: Button

    private var quizList: List<LessonQuizResponse> = listOf()
    private var currentQuestionIndex = 0
    private var answered = false

    private val apiService: ApiService by lazy {
        RetrofitInstance.api
    }

    private var lessonId: Long = -1L // <- lesson ID that we will fetch quizzes for

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Initialize Views
        questionText = findViewById(R.id.textQuestion)
        choiceButtons = listOf(
            findViewById(R.id.btnChoice1),
            findViewById(R.id.btnChoice2),
            findViewById(R.id.btnChoice3),
            findViewById(R.id.btnChoice4)
        )
        resultText = findViewById(R.id.textResult)
        nextButton = findViewById(R.id.btnNext)

        // Get lessonId from Intent extras
        lessonId = intent.getLongExtra("lessonId", -1)

        if (lessonId != -1L) {
            loadQuizFromApi(lessonId)
        } else {
            Toast.makeText(this, "No lesson ID provided", Toast.LENGTH_SHORT).show()
            finish()
        }

        choiceButtons.forEach { button ->
            button.setOnClickListener {
                if (!answered) {
                    checkAnswer(button.text.toString())
                }
            }
        }

        nextButton.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < quizList.size) {
                showQuestion()
            } else {
                showQuizFinished()
            }
        }

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun loadQuizFromApi(lessonId: Long) {
        lifecycleScope.launch {
            try {
                val response = apiService.getQuizzesByLessonId(lessonId)
                if (response.isSuccessful && response.body() != null) {
                    quizList = response.body()!!
                    showQuestion()
                } else {
                    Toast.makeText(this@LessonQuizActivity, "Failed to load quizzes", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LessonQuizActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun showQuestion() {
        answered = false
        resultText.visibility = View.GONE
        nextButton.visibility = View.GONE

        val currentQuiz = quizList[currentQuestionIndex]

        questionText.text = currentQuiz.question
        choiceButtons[0].text = currentQuiz.choice1
        choiceButtons[1].text = currentQuiz.choice2
        choiceButtons[2].text = currentQuiz.choice3
        choiceButtons[3].text = currentQuiz.choice4

        choiceButtons.forEach { button ->
            button.isEnabled = true
            button.setBackgroundResource(android.R.drawable.btn_default) // reset appearance
        }
    }

    private fun checkAnswer(selectedAnswer: String) {
        answered = true
        val correctAnswer = quizList[currentQuestionIndex].answer

        resultText.visibility = View.VISIBLE

        if (selectedAnswer == correctAnswer) {
            resultText.text = "Correct!"
            resultText.setTextColor(Color.parseColor("#4CAF50")) // Green
            resultText.setTypeface(null, Typeface.BOLD)
        } else {
            resultText.text = "Wrong!"
            resultText.setTextColor(Color.parseColor("#F44336")) // Red
            resultText.setTypeface(null, Typeface.BOLD)
        }

        choiceButtons.forEach { it.isEnabled = false }
        nextButton.visibility = View.VISIBLE
    }

    private fun showQuizFinished() {
        Toast.makeText(this, "Quiz Finished!", Toast.LENGTH_SHORT).show()
        finish()
    }
}

