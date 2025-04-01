package edu.cit.hapongo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboards",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"}))
public class Leaderboards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaderboardId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "lesson_id", nullable = false)
    private int lessonId;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Leaderboards() {
    }

    public Leaderboards(User user, int lessonId, int points, LocalDateTime updatedAt) {
        this.user = user;
        this.lessonId = lessonId;
        this.points = points;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getLeaderboardId() {
        return leaderboardId;
    }

    public void setLeaderboardId(int leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
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
                ", lessonId=" + lessonId +
                ", points=" + points +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
