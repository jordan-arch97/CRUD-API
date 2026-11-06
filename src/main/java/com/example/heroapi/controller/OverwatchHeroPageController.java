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

    // GET /about  -> templates/about.ftlh
    @GetMapping("/about")
    public String about() {
        return "about";
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
        addFormSettings(model, "Add a New Hero", "/characters", "Add Hero to Gallery", "add", "/");
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
            addFormSettings(model, "Add a New Hero", "/characters", "Add Hero to Gallery", "add", "/");
            model.addAttribute("errors", fieldErrors(result));
            return "character-form";
        }
        OverwatchHero saved = service.create(hero);
        redirect.addFlashAttribute("flash", saved.getName() + " was added");
        return "redirect:/"; // refreshing the gallery can no longer resubmit the form
    }

    // GET /characters/{id}/edit  -> same form, pre-filled with the saved values
    @GetMapping("/characters/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("hero", service.getById(id)); // throws 404 if missing
        addFormSettings(model, "Edit Hero", "/characters/" + id, "Save Changes", "", "/characters/" + id);
        return "character-form";
    }

    // POST /characters/{id}  -> update the EXISTING hero (service copies the fields onto it), then redirect
    @PostMapping("/characters/{id}")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("hero") OverwatchHero hero,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirect) {
        service.getById(id); // unknown id -> 404 page, even if the form also had errors
        if (result.hasErrors()) {
            addFormSettings(model, "Edit Hero", "/characters/" + id, "Save Changes", "", "/characters/" + id);
            model.addAttribute("errors", fieldErrors(result));
            return "character-form";
        }
        OverwatchHero saved = service.update(id, hero);
        redirect.addFlashAttribute("flash", saved.getName() + " was updated");
        return "redirect:/characters/" + id;
    }

    // POST /characters/{id}/delete  -> HTML forms can only send GET or POST, so delete is a POST
    @PostMapping("/characters/{id}/delete")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
        OverwatchHero hero = service.getById(id); // 404 page if it doesn't exist; also gives us the name
        service.delete(id);
        redirect.addFlashAttribute("flash", hero.getName() + " was removed");
        return "redirect:/";
    }

    // Runs only for exceptions thrown by THIS controller's methods, so the REST
    // controller still returns its normal JSON 404.
    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model, HttpServletResponse response) {
        response.setStatus(ex.getStatusCode().value());
        model.addAttribute("errorMessage", "We couldn't find a hero with that ID. It may have been removed.");
        return "error";
    }

    // The same form template is reused for add and edit, so these values change per page.
    private void addFormSettings(Model model, String title, String action, String submitLabel,
                                 String navActive, String cancelUrl) {
        model.addAttribute("cancelUrl", cancelUrl);
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
