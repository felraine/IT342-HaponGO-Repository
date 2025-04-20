package edu.cit.hapongo.service;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.repository.LeaderboardRepository;
import edu.cit.hapongo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    // Method to add or update the points for a user in a specific lesson
    public Leaderboards addOrUpdatePoints(User user, int lessonId, int points) {
        // Check if the leaderboard entry already exists
        Optional<Leaderboards> existingEntry = leaderboardRepository.findByUser_UserIdAndLessonId(user.getUserId(), lessonId);
        
        if (existingEntry.isPresent()) {
            // If entry exists, update points
            Leaderboards leaderboard = existingEntry.get();
            leaderboard.setPoints(points); 
            leaderboard.setUpdatedAt(LocalDateTime.now());
            return leaderboardRepository.save(leaderboard);
        } else {
            // If no entry exists, create a new one
            Leaderboards leaderboard = new Leaderboards(user, lessonId, points, LocalDateTime.now());
            return leaderboardRepository.save(leaderboard);
        }
    }

    // Method to get the total score for a user in a specific lesson
    public Optional<Leaderboards> getLeaderboardByUserAndLesson(User user, int lessonId) {
        return leaderboardRepository.findByUser_UserIdAndLessonId(user.getUserId(), lessonId);
    }

    // Method to get the leaderboard entries for a specific user
    public List<Leaderboards> getLeaderboardsForUser(User user) {
        return leaderboardRepository.findByUser_UserId(user.getUserId());
    }

    // Method to get the leaderboard entries for a specific lesson
    public List<Leaderboards> getTopLeaderboardForLesson(int lessonId) {
        // Add sorting or ranking logic here if needed
        return leaderboardRepository.findByLessonId(lessonId);
    }

    // Method to get the top 10 leaderboard entries across all lessons, sorted by points
    public List<Leaderboards> getTop10LeaderboardOverall() {
        // Fetch the top 10 leaderboard entries across all lessons, sorted by points in descending order
        return leaderboardRepository.findTop10ByOrderByPointsDesc();
    }
}
