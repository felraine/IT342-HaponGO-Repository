package edu.cit.hapongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lessonId;

    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    @Column(name = "lesson_order", nullable = false)
    private int lessonOrder;

    // Constructors
    public Lesson() {
    }

    public Lesson(String lessonName, int lessonOrder) {
        this.lessonName = lessonName;
        this.lessonOrder = lessonOrder;
    }

    // Getters and Setters
    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(int lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", lessonName='" + lessonName + '\'' +
                ", lessonOrder=" + lessonOrder +
                '}';
    }
}
