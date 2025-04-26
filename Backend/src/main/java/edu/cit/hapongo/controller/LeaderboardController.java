package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.service.LeaderboardService;
import edu.cit.hapongo.repository.LessonRepository;
import edu.cit.hapongo.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping("/update")
    public ResponseEntity<Leaderboards> addOrUpdatePoints(
            @RequestParam Long userId,   
            @RequestParam Long lessonId, 
            @RequestParam int points) {

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Lesson> lessonOpt = lessonRepository.findById(lessonId);

        if (!userOpt.isPresent() || !lessonOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        Lesson lesson = lessonOpt.get();

        Leaderboards leaderboard = leaderboardService.addOrUpdatePoints(user, lesson, points);
        
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<Leaderboards> getLeaderboardByUserAndLesson(
            @PathVariable Long userId,   
            @PathVariable Long lessonId) {

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Lesson> lessonOpt = lessonRepository.findById(lessonId);

        if (!userOpt.isPresent() || !lessonOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        Lesson lesson = lessonOpt.get();

        Optional<Leaderboards> leaderboard = leaderboardService.getLeaderboardByUserAndLesson(user, lesson);

        return leaderboard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Leaderboards>> getLeaderboardsForUser(@PathVariable Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (!userOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        List<Leaderboards> leaderboards = leaderboardService.getLeaderboardsForUser(user);

        return ResponseEntity.ok(leaderboards);
    }

    @GetMapping("/lesson/{lessonId}/top")
    public ResponseEntity<List<Leaderboards>> getTopLeaderboardForLesson(@PathVariable Long lessonId) {
        Optional<Lesson> lessonOpt = lessonRepository.findById(lessonId);

        if (!lessonOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Lesson lesson = lessonOpt.get();
        List<Leaderboards> leaderboards = leaderboardService.getTopLeaderboardForLesson(lesson);

        return ResponseEntity.ok(leaderboards);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Leaderboards>> getTop10LeaderboardOverall() {
        List<Leaderboards> leaderboards = leaderboardService.getTop10LeaderboardOverall();
        return ResponseEntity.ok(leaderboards);
    }
}
