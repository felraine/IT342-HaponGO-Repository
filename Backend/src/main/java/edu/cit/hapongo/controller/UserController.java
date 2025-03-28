package edu.cit.hapongo.controller;


import edu.cit.hapongo.model.User;
import edu.cit.hapongo.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password) {
        User user = userService.login(name, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid name or password");
        }
    }
    
    //READ
    @GetMapping("/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name) {
         return ResponseEntity.ok(userService.getUserByName(name));
    }

    //UPDATE HERE
    
    //DELETE HERE
}
