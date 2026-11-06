package com.example.heroapi.controller;

import com.example.heroapi.service.OverwatchHeroService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

// @Controller (not @RestController): the returned String is a TEMPLATE NAME, not JSON.
// The REST API lives in OverwatchHeroController and is not touched by this class.
@Controller
public class OverwatchHeroPageController {

    private final OverwatchHeroService service; // page controller -> service only

    public OverwatchHeroPageController(OverwatchHeroService service) {
        this.service = service;
    }

    // GET /                      -> everything
    // GET /?name=gen&role=Damage -> filtered (either, both, or neither may be present)
    @GetMapping("/")
    public String gallery(@RequestParam(name = "name", required = false) String name,
                          @RequestParam(name = "role", required = false) String role,
                          Model model) {
        model.addAttribute("heroes", service.search(name, role));
        // Send the typed values back so the search boxes keep them after searching
        model.addAttribute("searchName", name == null ? "" : name);
        model.addAttribute("searchRole", role == null ? "" : role);
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
