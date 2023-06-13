package com.example.apis.services;


import com.example.apis.model.User;
import com.example.apis.model.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    // Autowire the UserRepository
   private final UserRepository userRepository;

    // Create a constructor that takes a UserRepository as a parameter
    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Define a method that takes a String as a parameter and returns a User
    public User getUserByEmail(String email) {
        // Call the findByEmail method of the UserRepository
        // Return the User
        return userRepository.findByEmail(email);
    }
    
}
