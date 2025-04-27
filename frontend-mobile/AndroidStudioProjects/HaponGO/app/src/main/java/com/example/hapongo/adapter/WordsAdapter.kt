package com.example.hapongo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapongo.R
import com.example.hapongo.model.Dictionary

class WordsAdapter(private var words: List<Dictionary>) :
    RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val japaneseText: TextView = itemView.findViewById(R.id.textJapanese)
        val englishText: TextView = itemView.findViewById(R.id.textEnglish)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val wordItem = words[position]
        holder.japaneseText.text = wordItem.japaneseReading
        holder.englishText.text = wordItem.englishWord
    }

    override fun getItemCount(): Int {
        return words.size
    }

    fun updateWords(newWords: List<Dictionary>) {
        words = newWords
        notifyDataSetChanged()
    }
}
