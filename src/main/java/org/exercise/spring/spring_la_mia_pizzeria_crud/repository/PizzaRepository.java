package org.exercise.spring.spring_la_mia_pizzeria_crud.repository;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByNomeContainingIgnoreCase(String nome);
}