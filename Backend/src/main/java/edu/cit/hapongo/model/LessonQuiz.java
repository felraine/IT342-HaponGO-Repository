package edu.cit.hapongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson_quizzes")
public class LessonQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "choice1", nullable = false)
    private String choice1;

    @Column(name = "choice2", nullable = false)
    private String choice2;

    @Column(name = "choice3", nullable = false)
    private String choice3;

    @Column(name = "choice4", nullable = false)
    private String choice4;

    @Column(name = "answer", nullable = false)
    private String answer;

    // Constructors
    public LessonQuiz() {
    }

    public LessonQuiz(Lesson lesson, String question, String choice1, String choice2, String choice3, String choice4, String answer) {
        this.lesson = lesson;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
    }

    // Getters and Setters
    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "LessonQuiz{" +
                "questionId=" + questionId +
                ", lesson=" + (lesson != null ? lesson.getLessonId() : "null") +
                ", question='" + question + '\'' +
                ", choice1='" + choice1 + '\'' +
                ", choice2='" + choice2 + '\'' +
                ", choice3='" + choice3 + '\'' +
                ", choice4='" + choice4 + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}