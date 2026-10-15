package com.example.heroapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

// Not named "Character" so it can't collide with java.lang.Character.
@Entity
@Table(name = "characters")
public class OverwatchHero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // database auto-generates the ID
    private long characterId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    @Column(length = 1000)
    private String description;

    // Extra attribute 1: Tank, Damage, or Support (also used for the filter endpoint)
    private String role;

    // Extra attribute 2: the date the hero debuted (LocalDate, not java.util.Date)
    private LocalDate debutDate;

    public OverwatchHero() {
        // JPA requires a no-argument constructor
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getDebutDate() {
        return debutDate;
    }

    public void setDebutDate(LocalDate debutDate) {
        this.debutDate = debutDate;
    }
}
