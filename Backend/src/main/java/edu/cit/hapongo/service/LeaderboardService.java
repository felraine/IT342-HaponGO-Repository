package edu.cit.hapongo.service;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.repository.LeaderboardRepository;
import edu.cit.hapongo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Leaderboards addOrUpdatePoints(User user, Lesson lesson, int points) {
        Optional<Leaderboards> existingEntry = leaderboardRepository.findByUserAndLesson(user, lesson);

        if (existingEntry.isPresent()) {
            Leaderboards leaderboard = existingEntry.get();
            leaderboard.setPoints(points);
            leaderboard.setUpdatedAt(LocalDateTime.now());
            return leaderboardRepository.save(leaderboard);
        } else {
            Leaderboards leaderboard = new Leaderboards(user, lesson, points, LocalDateTime.now());
            return leaderboardRepository.save(leaderboard);
        }
    }

    public Optional<Leaderboards> getLeaderboardByUserAndLesson(User user, Lesson lesson) {
        return leaderboardRepository.findByUserAndLesson(user, lesson);
    }

    public List<Leaderboards> getLeaderboardsForUser(User user) {
        return leaderboardRepository.findByUser(user);
    }

    public List<Leaderboards> getTopLeaderboardForLesson(Lesson lesson) {
        List<Leaderboards> allLeaderboards = leaderboardRepository.findByLesson(lesson);

        return allLeaderboards.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getUser().getUserId(),
                        Collectors.summingInt(Leaderboards::getPoints)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Optional<User> userOpt = userRepository.findById(entry.getKey());
                    if (!userOpt.isPresent()) return null;

                    return new Leaderboards(userOpt.get(), lesson, entry.getValue(), LocalDateTime.now());
                })
                .filter(Objects::nonNull)
                .sorted((a, b) -> Integer.compare(b.getPoints(), a.getPoints()))
                .collect(Collectors.toList());
    }

    public List<Leaderboards> getTop10LeaderboardOverall() {
        return leaderboardRepository.findTop10ByOrderByPointsDesc();
    }

    public List<Leaderboards> getTop10OverallLeaderboards() {
        List<Leaderboards> allEntries = leaderboardRepository.findAll();

        return allEntries.stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getUser().getUserId(),
                        Collectors.summingInt(Leaderboards::getPoints)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Optional<User> userOpt = userRepository.findById(entry.getKey());
                    if (!userOpt.isPresent()) return null;

                    return new Leaderboards(userOpt.get(), null, entry.getValue(), LocalDateTime.now());
                })
                .filter(Objects::nonNull)
                .sorted((a, b) -> Integer.compare(b.getPoints(), a.getPoints()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
