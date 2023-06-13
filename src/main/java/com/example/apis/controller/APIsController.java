package com.example.apis.controller;

import com.example.apis.model.User;
import com.example.apis.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class APIsController {

    // Autowire the UserRepository
    @Autowired
    private UserRepository userRepository;

    // Display the home page
    @GetMapping("/home")
    public String home() {
        return "index";
    }

    // Redirect the user to the home page
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    // Display the register page
    @PostMapping("/home/register")
    public String register() {

        return "register";
    }

    /* 
     * // 1. Create a new user object and set its properties to the values passed in from the form
    User user = new User();
    user.setName(name);
    user.setPassword(password);
    user.setRepeatPassword(repeatPassword);
    user.setEmail(email);
    
    // 2. Save the new user object to the database
    userRepository.save(user);
    
    // 3. Redirect the user to the successregister page
    return "successregister";
     */
    @PostMapping("/home/successRegister")
    public String successRegister(@RequestParam("name") String name, @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("repeatPassword") String repeatPassword) {

        User userAlreadyExists = userRepository.findByEmail(email);
        if (userAlreadyExists != null) {
            return "redirect:/home/login?error=email";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRepeatPassword(repeatPassword);
        user.setEmail(email);

        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return "redirect:/home/login?error=password";
        }

        userRepository.save(user);

        return "successregister";
    }

    @GetMapping("/home/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            // Add model attribute to display error message on login page
            model.addAttribute("error", "Email or password is incorrect");
        }
        return "login";
    }

    // This method will be called when the user clicks the login button from the login page
    @PostMapping("/home/checkValidation")
    public String checkValidation(@RequestParam("email") String email,
            @RequestParam("password") String password) {
        try {
            // Get the user by the email provided in the login form
            User user = userRepository.findByEmail(email);
            // Check if the user exists and if the password matches the one stored in the database
            if (user != null && password.equals(user.getPassword())) {
                // If so, redirect the user to the /home page
                return "redirect:/home";
            } else {
                // If not, redirect the user to the /home/loginFail page
                return "redirect:/home/loginFail";
            }
        } catch (Exception ex) {
            // If an exception occurs, redirect the user to the /home/loginFail page
            ex.printStackTrace();
            return "redirect:/home/loginFail";
        }

    }

    // 1. Login fail page.
    @GetMapping("/home/loginFail")

    public String loginFail() {

        return "loginFail";

    }

}
