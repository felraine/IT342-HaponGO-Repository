package com.example.hapongo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageButton
import com.example.hapongo.model.Dictionary

class Dictionary : AppCompatActivity() {

    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText

    // Temporary default word list
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
        Dictionary(10, "Good night", "", "おやすみなさい"),
        Dictionary(11, "How are you?", "", "お元気ですか？"),
        Dictionary(12, "I'm fine", "", "元気です"),
        Dictionary(13, "What's your name?", "", "お名前は何ですか？"),
        Dictionary(14, "My name is...", "", "私の名前は..."),
        Dictionary(15, "Nice to meet you", "", "初めまして"),
        Dictionary(16, "I don't understand", "", "分かりません"),
        Dictionary(17, "Where is...?", "", "...はどこですか？"),
        Dictionary(18, "How much?", "", "いくらですか？"),
        Dictionary(19, "Help!", "", "助けて！"),
        Dictionary(20, "I love you", "", "愛してる")
    )

    private var currentWords = defaultWords.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

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
                if (query.isNotEmpty()) {
                    val filtered = defaultWords.filter {
                        it.english_word.contains(query, ignoreCase = true) ||
                                it.japanese_reading.contains(query)
                    }
                    currentWords.clear()
                    currentWords.addAll(filtered)
                    wordsAdapter.updateWords(currentWords)
                } else {
                    currentWords.clear()
                    currentWords.addAll(defaultWords)
                    wordsAdapter.updateWords(currentWords)
                }
            }
        })
    }
}
