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

    //@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<LessonContent> lessonContents;

    //@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<LessonQuiz> lessonQuizzes;

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

    /*public List<LessonContent> getLessonContents() {
        return lessonContents;
    }

    public void setLessonContents(List<LessonContent> lessonContents) {
        this.lessonContents = lessonContents;
    }

    public List<LessonQuiz> getLessonQuizzes() {
        return lessonQuizzes;
    }

    public void setLessonQuizzes(List<LessonQuiz> lessonQuizzes) {
        this.lessonQuizzes = lessonQuizzes;
    }*/

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", lessonName='" + lessonName + '\'' +
                ", maxScore=" + maxScore +
                '}';
    }
}