package com.example.heroapi.controller;

import com.example.heroapi.service.OverwatchHeroService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

// @Controller (not @RestController): the returned String is a TEMPLATE NAME, not JSON.
// The REST API lives in OverwatchHeroController and is not touched by this class.
@Controller
public class OverwatchHeroPageController {

    private final OverwatchHeroService service; // page controller -> service only

    public OverwatchHeroPageController(OverwatchHeroService service) {
        this.service = service;
    }

    // GET /  -> templates/index.ftlh
    @GetMapping("/")
    public String gallery(Model model) {
        model.addAttribute("heroes", service.getAll());
        return "index"; // no ".ftlh"
    }

    // GET /characters/{id}  -> templates/details.ftlh
    @GetMapping("/characters/{id}")
    public String details(@PathVariable("id") long id, Model model) {
        model.addAttribute("hero", service.getById(id)); // throws 404 if missing
        return "details";
    }

    // Runs only for exceptions thrown by THIS controller's methods, so the REST
    // controller still returns its normal JSON 404.
    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model, HttpServletResponse response) {
        response.setStatus(ex.getStatusCode().value());
        model.addAttribute("errorMessage", "We couldn't find a hero with that ID. It may have been removed.");
        return "error";
    }
}
