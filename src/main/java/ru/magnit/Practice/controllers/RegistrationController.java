package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magnit.Practice.models.User;
import ru.magnit.Practice.repos.UserRepository;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        return "regist";
    }

    @PostMapping("/registration")
    public String register(
            @RequestParam String userfamil,
            @RequestParam String username,
            @RequestParam String userpass,
            @RequestParam String usermail,
            @RequestParam(required = false) String sol,
            @RequestParam(required = false) String por,
            Model model
    ) {
        if (sol == null || por == null) return "redirect:/";
        User user = new User(username, userfamil, "o", usermail, userpass);
        userRepository.save(user);
        return "redirect:/";
    }
}
