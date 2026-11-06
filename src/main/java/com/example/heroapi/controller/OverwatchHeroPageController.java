package com.example.heroapi.controller;

import com.example.heroapi.entity.OverwatchHero;
import com.example.heroapi.service.OverwatchHeroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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

    // GET /characters/new  -> empty form
    // (Spring prefers this exact path over /characters/{id}, so "new" is never read as an id.)
    @GetMapping("/characters/new")
    public String newForm(Model model) {
        model.addAttribute("hero", new OverwatchHero());
        addFormSettings(model, "Add a New Hero", "/characters", "Add Hero to Gallery", "add");
        return "character-form";
    }

    // POST /characters  -> validate, save, then REDIRECT (Post/Redirect/Get)
    @PostMapping("/characters")
    public String create(@Valid @ModelAttribute("hero") OverwatchHero hero,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            // Show the form again. "hero" still holds what the person typed.
            addFormSettings(model, "Add a New Hero", "/characters", "Add Hero to Gallery", "add");
            model.addAttribute("errors", fieldErrors(result));
            return "character-form";
        }
        OverwatchHero saved = service.create(hero);
        redirect.addFlashAttribute("flash", saved.getName() + " was added");
        return "redirect:/"; // refreshing the gallery can no longer resubmit the form
    }

    // Runs only for exceptions thrown by THIS controller's methods, so the REST
    // controller still returns its normal JSON 404.
    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model, HttpServletResponse response) {
        response.setStatus(ex.getStatusCode().value());
        model.addAttribute("errorMessage", "We couldn't find a hero with that ID. It may have been removed.");
        return "error";
    }

    // The same form template is reused for add and edit, so these four values change per page.
    private void addFormSettings(Model model, String title, String action, String submitLabel, String navActive) {
        model.addAttribute("formTitle", title);
        model.addAttribute("formAction", action);
        model.addAttribute("submitLabel", submitLabel);
        model.addAttribute("navActive", navActive);
    }

    // Turns validation errors into a simple map: field name -> message
    private Map<String, String> fieldErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}
