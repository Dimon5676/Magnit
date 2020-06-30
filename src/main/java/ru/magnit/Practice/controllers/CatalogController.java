package ru.magnit.Practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;

@Controller
public class CatalogController {

    @Autowired
    IdeaRepository ideaRepository;

    @GetMapping("/catalog")
    public String catalog(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String search,
            Model model
    ){
        Iterable<Idea> ideas = ideaRepository.findAll();
        ArrayList<Idea> a = new ArrayList<>();
        if (filter != null && search != null) {
            if (filter.equalsIgnoreCase("")) {
                ideas.forEach(a::add);
            }
            if (filter.equalsIgnoreCase("title")) {
                for (Idea idea : ideas) {
                    if (idea.getTitle().contains(search)) {
                        a.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("name")) {
                for (Idea idea : ideas) {
                    if ((idea.getLastName() + idea.getName() + idea.getLastName()).contains(search)) {
                        a.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("description")) {
                for (Idea idea : ideas) {
                    if (idea.getDescription().contains(search)) {
                        a.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("subdivision")) {
                for (Idea idea : ideas) {
                    if (idea.getSubdivision().contains(search)) {
                        a.add(idea);
                    }
                }
            }
            if (filter.equalsIgnoreCase("status")) {
                for (Idea idea : ideas) {
                    if (idea.getStatus().contains(search)) {
                        a.add(idea);
                    }
                }
            }
        } else {
            ideas.forEach(a::add);
        }
        if (sort != null) {
            if (sort.equalsIgnoreCase("alphabet")) {
                a.sort(new AlphabetComp());
            }

            if (sort.equalsIgnoreCase("rate")) {
                a.sort(new RateComp());
            }

            if (sort.equalsIgnoreCase("date")) {
                a.sort(new DateComp());
            }
        } else {
            a.sort(new DateComp());
        }

        model.addAttribute ("ideas", a);
        return "catalog";
    }

    @PostMapping("/catalog")
    public String edit(
            @RequestParam String ideaChange,
            @RequestParam Long id,
            Model model
    ) {
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
        }
        if (ideaChange.equalsIgnoreCase("delete")) {
            Idea idea = ideaRepository.getById(id);
            ideaRepository.delete(idea);
        }
        return "redirect:/catalog";
    }

}
