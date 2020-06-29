package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;

@Controller
public class CatalogController {

    @Autowired
    IdeaRepository ideaRepository;

    @GetMapping("/catalog")
    public String catalog(Model model){
        Iterable<Idea> ideas = ideaRepository.findAll ();
        model.addAttribute ("ideas", ideas);
        return "catalog";
    }

}
