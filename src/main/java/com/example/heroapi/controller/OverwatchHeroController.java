package com.example.heroapi.controller;

import com.example.heroapi.entity.OverwatchHero;
import com.example.heroapi.service.OverwatchHeroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class OverwatchHeroController {

    private final OverwatchHeroService service; // controller -> service only, never the repository

    public OverwatchHeroController(OverwatchHeroService service) {
        this.service = service;
    }

    // GET /api/characters                -> all
    // GET /api/characters?role=Damage    -> filter by role
    // GET /api/characters?name=tra       -> partial, case-insensitive name search
    @GetMapping
    public List<OverwatchHero> getHeroes(
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isBlank()) {
            return service.searchByName(name);
        }
        if (role != null && !role.isBlank()) {
            return service.getByRole(role);
        }
        return service.getAll();
    }

    // GET /api/characters/{id} -> 200, or 404 if not found
    @GetMapping("/{id}")
    public OverwatchHero getHero(@PathVariable("id") long id) {
        return service.getById(id);
    }

    // POST /api/characters -> 201, or 400 if name/description is blank
    @PostMapping
    public ResponseEntity<OverwatchHero> createHero(@Valid @RequestBody OverwatchHero hero) {
        OverwatchHero saved = service.create(hero);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/characters/{id} -> 200, 400 if invalid, 404 if not found
    @PutMapping("/{id}")
    public OverwatchHero updateHero(@PathVariable("id") long id,
                                    @Valid @RequestBody OverwatchHero hero) {
        return service.update(id, hero);
    }

    // DELETE /api/characters/{id} -> 204, or 404 if not found
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHero(@PathVariable("id") long id) {
        service.delete(id);
    }
}
