package ru.magnit.Practice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static int count = 0;

    @GetMapping("/")
    public String homePage(Model model) {
        count++;
        model.addAttribute("count", count);
        return "home";
    }
}
