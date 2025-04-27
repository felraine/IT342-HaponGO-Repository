package com.example.hapongo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
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
        holder.japaneseText.text = wordItem.japanese_reading
        holder.englishText.text = wordItem.english_word
    }

    override fun getItemCount(): Int {
        return words.size
    }

    fun updateWords(newWords: List<Dictionary>) {
        words = newWords
        notifyDataSetChanged()
    }
}
