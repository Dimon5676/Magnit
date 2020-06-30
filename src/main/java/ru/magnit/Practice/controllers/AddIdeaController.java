package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;

import javax.persistence.Id;
import java.util.Date;

@Controller
public class AddIdeaController {

    @Autowired
    private IdeaRepository ideaRepository;

    @GetMapping("/add")
    public String addIdea(Model model) {
        return "add";
    }

    @PostMapping("/add")
    public String addNewIdea(
            @RequestParam String name,
            @RequestParam String lastName,
            @RequestParam String middleName,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String email,
            @RequestParam(required = false) String subdivision,
            @RequestParam(required = false) String sol,
            @RequestParam(required = false) String por, Model model
    ) {
        if (sol == null || por == null) return "add";
        Idea idea = new Idea(
                name,
                middleName,
                lastName,
                email,
                title,
                description,
                "test",
                "waiting",
                new Date(),
                0);
        ideaRepository.save(idea);
        return "redirect:/";
    }

}

