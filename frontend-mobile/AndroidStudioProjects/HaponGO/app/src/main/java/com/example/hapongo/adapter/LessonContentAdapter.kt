package com.example.hapongo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapongo.R
import com.example.hapongo.model.LessonContent

class LessonContentAdapter(private var lessonList: List<LessonContent>) : RecyclerView.Adapter<LessonContentAdapter.LessonContentViewHolder>() {

    inner class LessonContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val japaneseWord: TextView = itemView.findViewById(R.id.textJapaneseWord)
        val pronunciation: TextView = itemView.findViewById(R.id.textPronunciation)
        val englishWord: TextView = itemView.findViewById(R.id.textEnglishWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonContentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson_content, parent, false)
        return LessonContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonContentViewHolder, position: Int) {
        val lesson = lessonList[position]
        holder.japaneseWord.text = lesson.japaneseWord
        holder.pronunciation.text = lesson.pronunciation
        holder.englishWord.text = lesson.englishWord
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    fun updateData(newLessonList: List<LessonContent>) {
        lessonList = newLessonList
        notifyDataSetChanged()
    }
}
