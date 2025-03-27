package org.exercise.spring.spring_la_mia_pizzeria_crud.service;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.exercise.spring.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    public List<Ingrediente> findAllSortedByName() {
        return ingredienteRepository.findAll(Sort.by("nome"));
    }

    public List<Ingrediente> findByName(String nome) {
        return ingredienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Ingrediente getById(Integer id) {
        return ingredienteRepository.findById(id).get();
    }

    public Ingrediente create(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente update(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public void delete(Ingrediente ingrediente) {

        for (Pizza pizza : ingrediente.getPizze()) {
            pizza.getIngredienti().remove(ingrediente);
        }

        ingredienteRepository.delete(ingrediente);
    }

    public void deleteById(Integer id) {

        Ingrediente ingredienteDaEliminare = getById(id);

        for (Pizza pizzaCollegata : ingredienteDaEliminare.getPizze()) {
            pizzaCollegata.getIngredienti().remove(ingredienteDaEliminare);
        }

        ingredienteRepository.delete(ingredienteDaEliminare);
    }
}
