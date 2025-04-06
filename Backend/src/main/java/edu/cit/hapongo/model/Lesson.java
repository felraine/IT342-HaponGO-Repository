package edu.cit.hapongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonId;

    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    @Column(name = "max_score", nullable = false)
    private int maxScore;

    // Constructors
    public Lesson() {
    }

    public Lesson(String lessonName, int maxScore) {
        this.lessonName = lessonName;
        this.maxScore = maxScore;
    }

    // Getters and Setters
    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", lessonName='" + lessonName + '\'' +
                ", maxScore=" + maxScore +
                '}';
    }
}
