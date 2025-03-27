package org.exercise.spring.spring_la_mia_pizzeria_crud.service;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.OffertaSpeciale;
import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.exercise.spring.spring_la_mia_pizzeria_crud.repository.OffertaSpecialeRepository;
import org.exercise.spring.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OffertaSpecialeRepository offertaSpecialeRepository;

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public List<Pizza> findAllSortedByName() {
        return pizzaRepository.findAll(Sort.by("nome"));
    }

    public List<Pizza> findAllSortedByPrice() {
        return pizzaRepository.findAll(Sort.by("prezzo"));
    }

    public Pizza getById(Integer id) {
        return pizzaRepository.findById(id).get();
    }

    public List<Pizza> findByName(String nome) {
        return pizzaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public void delete(Pizza pizza) {

        for (OffertaSpeciale offertaDaCancellare : pizza.getOfferteSpeciali()) {
            offertaSpecialeRepository.delete(offertaDaCancellare);
        }

        pizzaRepository.delete(pizza);
    }

    public void deleteById(Integer id) {

        Pizza pizza = getById(id);

        for (OffertaSpeciale offertaDaCancellare : pizza.getOfferteSpeciali()) {
            offertaSpecialeRepository.delete(offertaDaCancellare);
        }

        pizzaRepository.delete(pizza);
    }
}
