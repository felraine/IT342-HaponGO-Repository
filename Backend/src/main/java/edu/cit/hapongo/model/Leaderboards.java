package edu.cit.hapongo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboards",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"}))
public class Leaderboards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long leaderboardId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Leaderboards() {
    }

    public Leaderboards(User user, Lesson lesson, int points, LocalDateTime updatedAt) {
        this.user = user;
        this.lesson = lesson;
        this.points = points;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public long getLeaderboardId() {
        return leaderboardId;
    }

    public void setLeaderboardId(long leaderboardId) {
        this.leaderboardId = leaderboardId;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Override toString
    @Override
    public String toString() {
        return "Leaderboards{" +
                "leaderboardId=" + leaderboardId +
                ", user=" + user.getName() +
                ", lesson=" + lesson.getLessonName() + // assuming Lesson has a getLessonName() method
                ", points=" + points +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
