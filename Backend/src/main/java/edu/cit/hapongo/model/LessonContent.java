package edu.cit.hapongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson_contents")
public class LessonContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonContentId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "japanese_word", nullable = false)
    private String japaneseWord;

    @Column(name = "pronunciation", nullable = false)
    private String pronunciation;

    @Column(name = "english_word", nullable = false)
    private String englishWord;

    // Constructors
    public LessonContent() {
    }

    public LessonContent(Lesson lesson, String japaneseWord, String pronunciation, String englishWord) {
        this.lesson = lesson;
        this.japaneseWord = japaneseWord;
        this.pronunciation = pronunciation;
        this.englishWord = englishWord;
    }

    // Getters and Setters
    public int getLessonContentId() {
        return lessonContentId;
    }

    public void setLessonContentId(int lessonContentId) {
        this.lessonContentId = lessonContentId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getJapaneseWord() {
        return japaneseWord;
    }

    public void setJapaneseWord(String japaneseWord) {
        this.japaneseWord = japaneseWord;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    @Override
    public String toString() {
        return "LessonContent{" +
                "lessonContentId=" + lessonContentId +
                ", lessonId=" + (lesson != null ? lesson.getLessonId() : "null") +
                ", japaneseWord='" + japaneseWord + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", englishWord='" + englishWord + '\'' +
                '}';
    }
}
