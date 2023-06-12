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

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @PostMapping("/home/register")
    public String register() {

        return "register";
    }

    @PostMapping("/home/successRegister")
    public String successRegister(@RequestParam("name") String name, @RequestParam("password") String password,
                                  @RequestParam("email") String email,
                                  @RequestParam("repeatPassword") String repeatPassword) {


        System.out.println("name: " + name);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        System.out.println("repeatPassword: " + repeatPassword);

        User userAlreadyExists = userRepository.findByEmail(email);
        if (userAlreadyExists != null) {
            return "redirect:/home/login?error=email";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRepeatPassword(repeatPassword);
        user.setEmail(email);



        userRepository.save(user);

        return "successregister";
    }

    @GetMapping("/home/login")
    public String login(@RequestParam(value = "error",required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Email already exists");
        }
        return "login";
    }

    @PostMapping("/home/checkValidation")
    public String checkValidation(@RequestParam("email") String email,
                                  @RequestParam("password") String password) {
        System.out.println("email: " + email);
        System.out.println("password: " + password);


        User user = userRepository.findByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            return "redirect:/home";
        } else {
            return "redirect:/home/loginFail";
        }

    }

    @GetMapping("/home/loginFail")
    public String loginFail() {
        return "loginFail";
    }


}
