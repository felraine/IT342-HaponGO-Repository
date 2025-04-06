package edu.cit.hapongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_lessons")
public class UserLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    // Constructors
    public UserLesson() {
    }

    public UserLesson(User user, Lesson lesson) {
        this.user = user;
        this.lesson = lesson;
        this.isActive = false; //default value
        this.isCompleted = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "UserLesson{" +
                "id=" + id +
                ", user=" + user +
                ", lesson=" + lesson +
                ", isActive=" + isActive +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
