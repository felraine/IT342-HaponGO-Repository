package edu.cit.hapongo.dto;

public class OverallLeaderboardDTO {
    private Long userId;
    private String userName;
    private Integer totalPoints;

    public OverallLeaderboardDTO(Long userId, String userName, Integer totalPoints) {
        this.userId = userId;
        this.userName = userName;
        this.totalPoints = totalPoints;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}
