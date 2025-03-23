package edu.cit.hapongo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password) {
        User user = userService.login(name, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid name or password");
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        try {
            User registeredUser = userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
