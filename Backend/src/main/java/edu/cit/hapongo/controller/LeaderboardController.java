package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    // Endpoint to add or update points for a user in a specific lesson
    @PostMapping("/update")
    public ResponseEntity<Leaderboards> addOrUpdatePoints(
            @RequestParam int userId,   
            @RequestParam int lessonId, 
            @RequestParam int points) {
        
        User user = new User();
        user.setUserId(userId);
        
        Leaderboards leaderboard = leaderboardService.addOrUpdatePoints(user, lessonId, points);
        
        return ResponseEntity.ok(leaderboard);
    }

    // Endpoint to get the leaderboard by user and lesson
    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<Leaderboards> getLeaderboardByUserAndLesson(
            @PathVariable int userId,   
            @PathVariable int lessonId) {
        
        User user = new User();
        user.setUserId(userId); 
        
        Optional<Leaderboards> leaderboard = leaderboardService.getLeaderboardByUserAndLesson(user, lessonId);
        
        return leaderboard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get the leaderboard entries for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Leaderboards>> getLeaderboardsForUser(@PathVariable Long userId) {
        User user = new User();
        user.setUserId(userId.intValue()); 
        
        List<Leaderboards> leaderboards = leaderboardService.getLeaderboardsForUser(user);
        
        return ResponseEntity.ok(leaderboards);
    }

    // Endpoint to get the top leaderboard for a specific lesson
    @GetMapping("/lesson/{lessonId}/top")
    public ResponseEntity<List<Leaderboards>> getTopLeaderboardForLesson(@PathVariable int lessonId) {
        List<Leaderboards> leaderboards = leaderboardService.getTopLeaderboardForLesson(lessonId);
        
        return ResponseEntity.ok(leaderboards);
    }

    // Endpoint to get the top 10 leaderboard entries across all lessons
    @GetMapping("/top10")
    public ResponseEntity<List<Leaderboards>> getTop10LeaderboardOverall() {
        List<Leaderboards> leaderboards = leaderboardService.getTop10LeaderboardOverall();
        
        return ResponseEntity.ok(leaderboards);
    }
}
