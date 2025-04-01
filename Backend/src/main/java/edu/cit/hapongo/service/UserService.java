    package edu.cit.hapongo.service;

    import edu.cit.hapongo.model.User;
    import edu.cit.hapongo.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import edu.cit.hapongo.exception.UserAlreadyExistsException;
    import java.util.List;

    import java.time.LocalDateTime;
    import java.util.Optional;

    @Service
    public class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder; 

        public User login(String email, String password) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return user; // Password matches
                }
            }
            return null; // Invalid email or password
        }

        //register user
        public User register(User user) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("Email already in use");
            }
        
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountCreationDate(LocalDateTime.now());
            return userRepository.save(user);
        }

        //get user by email
        public Optional<User> getUserByEmail(String email) {
            return userRepository.findByEmail(email);
        }
        //get all users
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
        //get user by id
        public Optional<User> getUserById(int id) {
            return userRepository.findById(id);
        }
        //update user
        public User updateUser(int userId, User userDetails) {
            Optional<User> existingUserOptional = userRepository.findById(userId);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                
                if (userDetails.getName() != null && !userDetails.getName().isBlank()) {
                    System.out.println("Updating name: " + userDetails.getName());
                    existingUser.setName(userDetails.getName());
                }
        
                if (userDetails.getEmail() != null && !userDetails.getEmail().isBlank()) {
                    System.out.println("Updating email: " + userDetails.getEmail());
                    if (userRepository.findByEmail(userDetails.getEmail()).isPresent() &&
                        !existingUser.getEmail().equals(userDetails.getEmail())) {
                        throw new RuntimeException("Email is already in use by another user");
                    }
                    existingUser.setEmail(userDetails.getEmail());
                }
        
                if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
                    System.out.println("Updating password: " + userDetails.getPassword());
                    existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                }
        
                return userRepository.save(existingUser);
            } else {
                throw new RuntimeException("User not found with ID: " + userId);
            }
        }
        //delete user
        public void deleteUser(int userId) {
            Optional<User> existingUserOptional = userRepository.findById(userId);
            if (existingUserOptional.isPresent()) {
                userRepository.delete(existingUserOptional.get());
            } else {
                throw new RuntimeException("User not found with ID: " + userId);
            }
        }
    }