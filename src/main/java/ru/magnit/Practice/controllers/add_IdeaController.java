package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;

@Controller
public class add_IdeaController {

    String status ="";
    Long authorId = 0L;

    @Autowired
    private IdeaRepository ideaRepository;

    @GetMapping("/home")
    public String ideas(Model model) {
        Iterable<Idea> ideas = ideaRepository.findAll ();
        model.addAttribute ("ideas", ideas);
        return "redirect:/home";
    }

    @GetMapping("/add")
    public String addIdea(Model model) {
        return "/add";
    }
    @PostMapping("/add")
    public String addNewIdea(@RequestParam String title, @RequestParam String description,
                             @RequestParam String subdivision , Model model) {

        Idea idea = new Idea (title,description,subdivision,status,authorId);
        ideaRepository.save (idea);
        return "redirect:/home";
    }

}

