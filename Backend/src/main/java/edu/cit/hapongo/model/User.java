package edu.cit.hapongo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "isAdmin", nullable = false)
    private boolean isAdmin;

    @Column(name = "subscriptionStatus", nullable = false)
    private boolean subscriptionStatus;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    @JsonIgnore
    private byte[] profilePicture;

    @Column(name = "accountCreationDate")
    private LocalDateTime accountCreationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  
    private List<Leaderboards> leaderboards;

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(boolean subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public List<Leaderboards> getLeaderboards() {
        return leaderboards;
    }

    public void setLeaderboards(List<Leaderboards> leaderboards) {
        this.leaderboards = leaderboards;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                ", subscriptionStatus=" + subscriptionStatus +
                ", accountCreationDate=" + accountCreationDate +
                '}';
    }
}
