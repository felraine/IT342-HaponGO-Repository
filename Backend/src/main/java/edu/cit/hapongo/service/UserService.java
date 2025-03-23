package edu.cit.hapongo.service;

import edu.cit.hapongo.model.User;
import edu.cit.hapongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
 
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public User login(String name, String password) {
        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Comparing password: " + password + " with " + user.getPassword());
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user; // Password matches
            }
        }
        return null; // Invalid name or password
    }
    
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountCreationDate(LocalDateTime.now());
        return userRepository.save(user);
    }
}


