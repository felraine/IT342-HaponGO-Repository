package com.example.hapongo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hapongo.model.Dictionary
import com.example.hapongo.network.ApiService
import com.example.hapongo.network.RetrofitInstance
import kotlinx.coroutines.launch

class Dictionary : AppCompatActivity() {

    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var apiService: ApiService

    // Temporary default word list (for local data)
    private val defaultWords = listOf(
        Dictionary(1, "Hello", "", "こんにちは"),
        Dictionary(2, "Goodbye", "", "さようなら"),
        Dictionary(3, "Thank you", "", "ありがとう"),
        Dictionary(4, "Yes", "", "はい"),
        Dictionary(5, "No", "", "いいえ"),
        Dictionary(6, "Please", "", "お願いします"),
        Dictionary(7, "Sorry", "", "ごめんなさい"),
        Dictionary(8, "Excuse me", "", "すみません"),
        Dictionary(9, "Good morning", "", "おはようございます"),
        Dictionary(10, "Good night", "", "おやすみなさい")
    )

    private var currentWords = defaultWords.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        // Initialize the API service
        apiService = RetrofitInstance.api

        recyclerView = findViewById(R.id.wordsRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)

        recyclerView.layoutManager = LinearLayoutManager(this)
        wordsAdapter = WordsAdapter(currentWords)
        recyclerView.adapter = wordsAdapter

        setupSearch()

        val back = findViewById<ImageButton>(R.id.btnBack)
        back.setOnClickListener {
            finish()
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()

                // Perform search when the query is not empty
                if (query.isNotEmpty()) {
                    searchWords(query)
                } else {
                    // If query is empty, reset to the default list
                    currentWords.clear()
                    currentWords.addAll(defaultWords)
                    wordsAdapter.updateWords(currentWords)
                }
            }
        })
    }

    // Function to call the backend search API
    private fun searchWords(query: String) {
        lifecycleScope.launch {
            try {
                // Call the search API
                val response = apiService.searchDictionary(query)
                if (response.isSuccessful && response.body() != null) {
                    // If successful, update the word list with the filtered words
                    val filteredWords = response.body()!!
                    currentWords.clear()
                    currentWords.addAll(filteredWords)
                    wordsAdapter.updateWords(currentWords)
                } else {
                    // If no words found, show a toast
                    Toast.makeText(this@Dictionary, "No words found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle network or other errors
                Toast.makeText(this@Dictionary, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
