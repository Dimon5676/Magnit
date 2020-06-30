package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    IdeaRepository ideaRepository;

    @GetMapping("/catalog")
    public String catalog(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String filter,
            Model model
    ){
        Iterable<Idea> ideas = ideaRepository.findAll();
        if (sort == null) {
            model.addAttribute ("ideas", ideas);
            return "catalog";
        }
        if (sort.equalsIgnoreCase("alphabet")) {
            //Сортировка по ideas по алфавиту
        }

        if (sort.equalsIgnoreCase("rate")) {
            //Сортировка по ideas по популярности
        }

        if (sort.equalsIgnoreCase("date")) {
            //Сортировка по ideas по дате
        }
        model.addAttribute ("ideas", ideas);
        return "catalog";
    }

}
