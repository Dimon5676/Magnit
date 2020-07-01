package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;
import ru.magnit.Practice.validators.StringFieldValidator;

import java.util.ArrayList;
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
            @RequestParam String subdivision,
            @RequestParam(required = false) String sol,
            @RequestParam(required = false) String por, Model model
    ) {
        if (sol == null || por == null) return "add";
        ArrayList<Boolean> arr = new ArrayList<>();
        arr.add(StringFieldValidator.isNumbersInString(name) || name.isEmpty());
        arr.add(StringFieldValidator.isNumbersInString(lastName) || lastName.isEmpty());
        arr.add(StringFieldValidator.isNumbersInString(middleName));
        arr.add(!StringFieldValidator.isEmail(email));
        arr.add(title.isEmpty());
        arr.add(description.isEmpty());
        model.addAttribute("nameError", arr.get(0));
        model.addAttribute("lastNameError", arr.get(1));
        model.addAttribute("middleNameError", arr.get(2));
        model.addAttribute("emailError", arr.get(3));
        model.addAttribute("titleError", arr.get(4));
        model.addAttribute("descriptionError", arr.get(5));
        if (arr.contains(true)) {
            return "add";
        }
        Idea idea = new Idea(
                name,
                middleName,
                lastName,
                email,
                title,
                description,
                subdivision,
                "Не рассмотрена",
                new Date(),
                0);
        ideaRepository.save(idea);
        return "redirect:/";
    }

}

