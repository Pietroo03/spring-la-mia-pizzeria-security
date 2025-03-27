package org.exercise.spring.spring_la_mia_pizzeria_crud.repository;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    List<Ingrediente> findByNomeContainingIgnoreCase(String nome);
}
