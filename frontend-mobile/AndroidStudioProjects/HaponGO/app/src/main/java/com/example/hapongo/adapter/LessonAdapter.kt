package com.example.hapongo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapongo.R
import com.example.hapongo.model.Lesson

class LessonAdapter(private val onItemClick: (Lesson) -> Unit) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    private var lessons: List<Lesson> = emptyList()

    fun submitList(list: List<Lesson>) {
        lessons = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons[position]
        holder.bind(lesson)
    }

    override fun getItemCount() = lessons.size

    inner class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.lessonTitle)

        fun bind(lesson: Lesson) {
            title.text = lesson.lessonName

            itemView.setOnClickListener {
                onItemClick(lesson) // 🔥 pass the clicked lesson
            }
        }
    }
}
