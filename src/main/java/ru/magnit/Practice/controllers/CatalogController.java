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
import ru.magnit.Practice.comparators.AlphabetComp;
import ru.magnit.Practice.comparators.DateComp;
import ru.magnit.Practice.comparators.RateComp;
import ru.magnit.Practice.models.Idea;
import ru.magnit.Practice.repos.IdeaRepository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class CatalogController {

    @Autowired
    IdeaRepository ideaRepository;

    @Value("${spring.mail.username}")
    String mail;

    @Autowired
    public EmailService emailService;

    @GetMapping("/catalog")
    public String catalog(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String search,
            Model model
    ){
        Iterable<Idea> ideas = ideaRepository.findAll();
        ArrayList<Idea> userIdeas = new ArrayList<>();
        ArrayList<Idea> adminIdeas = new ArrayList<>();
        if (filter != null && search != null) {
            if (filter.equalsIgnoreCase("")) {
                for (Idea idea : ideas) {
                    if (idea.getStatus().equalsIgnoreCase("Рассмотрена")) {
                        userIdeas.add(idea);
                    } else {
                        adminIdeas.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("title")) {
                for (Idea idea : ideas) {
                    if (idea.getTitle().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Рассмотрена")) {
                        userIdeas.add(idea);
                    } else if (idea.getTitle().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Не рассмотрена")) {
                        adminIdeas.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("name")) {
                for (Idea idea : ideas) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(idea.getLastName());
                    builder.append(idea.getName());
                    builder.append(idea.getMiddleName());
                    if (builder.toString().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Рассмотрена")) {
                        userIdeas.add(idea);
                    } else if (builder.toString().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Не рассмотрена")) {
                        adminIdeas.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("description")) {
                for (Idea idea : ideas) {
                    if (idea.getDescription().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Рассмотрена")) {
                        userIdeas.add(idea);
                    } else if (idea.getDescription().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Не рассмотрена")) {
                        adminIdeas.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("subdivision")) {
                for (Idea idea : ideas) {
                    if (idea.getSubdivision().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Рассмотрена")) {
                        userIdeas.add(idea);
                    } else if (idea.getSubdivision().toLowerCase().contains(search.toLowerCase()) && idea.getStatus().equalsIgnoreCase("Не рассмотрена")) {
                        adminIdeas.add(idea);
                    }
                }
            }
        } else {
            for (Idea idea : ideas) {
                if (idea.getStatus().equalsIgnoreCase("Рассмотрена")) {
                    userIdeas.add(idea);
                } else {
                    adminIdeas.add(idea);
                }
            }
        }
        if (sort != null) {
            if (sort.equalsIgnoreCase("alphabet")) {
                userIdeas.sort(new AlphabetComp());
                adminIdeas.sort(new AlphabetComp());
            }

            if (sort.equalsIgnoreCase("rate")) {
                userIdeas.sort(new RateComp());
                adminIdeas.sort(new RateComp());
            }

            if (sort.equalsIgnoreCase("date")) {
                userIdeas.sort(new DateComp());
                adminIdeas.sort(new DateComp());
            }
        } else {
            userIdeas.sort(new DateComp());
            adminIdeas.sort(new DateComp());
        }

        model.addAttribute ("userIdeas", userIdeas);
        model.addAttribute ("adminIdeas", adminIdeas);
        return "catalog";
    }

    @PostMapping("/catalog")
    public String edit(
            @RequestParam String ideaChange,
            @RequestParam Long id,
            Model model
    ) throws UnsupportedEncodingException, AddressException {
        if (ideaChange == null || id == null) return "redirect:/catalog";
        if (ideaChange.equalsIgnoreCase("waiting")) {
            Idea idea = ideaRepository.getById(id);
            idea.setStatus("Не рассмотрена");
            ideaRepository.save(idea);
        }
        if (ideaChange.equalsIgnoreCase("checked")) {
            Idea idea = ideaRepository.getById(id);
            idea.setStatus("Рассмотрена");
            ideaRepository.save(idea);
            sendEmailWithoutTemplating(idea.getLastName() + " " + idea.getName() + " " + idea.getMiddleName(), idea.getEmail());
        }
        if (ideaChange.equalsIgnoreCase("delete")) {
            Idea idea = ideaRepository.getById(id);
            ideaRepository.delete(idea);
        }
        return "redirect:/catalog";
    }

    public void sendEmailWithoutTemplating(String name, String mailTo) throws UnsupportedEncodingException, AddressException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress(mail, "Magnit"))
                .to(Lists.newArrayList(new InternetAddress(mailTo, "")))
                .subject("It for people")
                .body("Здравствуйте, " + name + ", статус вашей идеи на проекте \"IT для людей\" был изменён " + new Date())
                .encoding("UTF-8").build();

        emailService.send(email);
    }

    @GetMapping("/like")
    public String like(
            @RequestParam Long like,
            Model model
    ) {
        Idea idea = ideaRepository.getById(like);
        idea.setRate(idea.getRate() + 1);
        ideaRepository.save(idea);
        return "redirect:/catalog";
    }
}
