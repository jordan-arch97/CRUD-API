package com.example.heroapi.service;

import com.example.heroapi.entity.OverwatchHero;
import com.example.heroapi.repository.OverwatchHeroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OverwatchHeroService {

    private final OverwatchHeroRepository repository;

    public OverwatchHeroService(OverwatchHeroRepository repository) {
        this.repository = repository;
    }

    public List<OverwatchHero> getAll() {
        return repository.findAll();
    }

    public List<OverwatchHero> getByRole(String role) {
        return repository.findByRoleIgnoreCase(role);
    }

    public List<OverwatchHero> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // Used by the gallery page: apply whichever filters were filled in (both, one, or none).
    public List<OverwatchHero> search(String name, String role) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasRole = role != null && !role.isBlank();
        if (hasName && hasRole) {
            return repository.findByNameContainingIgnoreCaseAndRoleIgnoreCase(name.trim(), role.trim());
        }
        if (hasName) {
            return searchByName(name.trim());
        }
        if (hasRole) {
            return getByRole(role.trim());
        }
        return getAll();
    }

    public OverwatchHero getById(long id) {
        // Missing ID -> 404
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id));
    }

    public OverwatchHero create(OverwatchHero hero) {
        hero.setCharacterId(0); // ignore any ID the client sends; the database generates it
        return repository.save(hero);
    }

    public OverwatchHero update(long id, OverwatchHero updated) {
        OverwatchHero existing = getById(id); // throws 404 if it doesn't exist
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setRole(updated.getRole());
        existing.setDebutDate(updated.getDebutDate());
        existing.setImageUrl(updated.getImageUrl());
        return repository.save(existing);
    }

    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id);
        }
        repository.deleteById(id);
    }
}
