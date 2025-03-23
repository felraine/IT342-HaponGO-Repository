package edu.cit.hapongo.service;

import edu.cit.hapongo.model.User;
import edu.cit.hapongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Get user by name
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
    
}
