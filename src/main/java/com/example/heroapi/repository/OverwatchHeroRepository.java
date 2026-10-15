package com.example.heroapi.repository;

import com.example.heroapi.entity.OverwatchHero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository already gives us findAll, findById, save, deleteById, existsById.
// Spring Data builds the two queries below from the method names alone.
public interface OverwatchHeroRepository extends JpaRepository<OverwatchHero, Long> {

    // WHERE LOWER(role) = LOWER(?)
    List<OverwatchHero> findByRoleIgnoreCase(String role);

    // WHERE LOWER(name) LIKE LOWER('%?%')  -> partial, case-insensitive
    List<OverwatchHero> findByNameContainingIgnoreCase(String name);
}
