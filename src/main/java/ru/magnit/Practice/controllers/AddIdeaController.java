package ru.magnit.Practice.controllers;

import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;
import ru.magnit.Practice.validators.StringFieldValidator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class AddIdeaController {

    @Value("${spring.mail.username}")
    String mail;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    public EmailService emailService;

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
    ) throws UnsupportedEncodingException, AddressException {
        if (sol == null || por == null) return "add";
        ArrayList<Boolean> arr = new ArrayList<>();
        arr.add(StringFieldValidator.isNumbersInString(name) || name.isEmpty());
        arr.add(StringFieldValidator.isNumbersInString(lastName) || lastName.isEmpty());
        arr.add(StringFieldValidator.isNumbersInString(middleName));
        try {
            new InternetAddress(email);
            arr.add(false);
        } catch (AddressException e) {
            arr.add(true);
        }
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
        sendEmailWithoutTemplating(lastName + " " + name + " " + middleName);
        return "redirect:/";
    }

    public void sendEmailWithoutTemplating(String name) throws UnsupportedEncodingException, AddressException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress(mail, "Magnit"))
                .to(Lists.newArrayList(new InternetAddress(mail, "Magnit")))
                .subject("It for people")
                .body("Пользователь " + name + " оставил(-а) идею на сайте " + new Date())
                .encoding("UTF-8").build();

        emailService.send(email);
    }
}

