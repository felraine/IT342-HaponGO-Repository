package edu.cit.hapongo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //get user by name
    @GetMapping("/name/{name}")
    public Optional<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }
    
}
