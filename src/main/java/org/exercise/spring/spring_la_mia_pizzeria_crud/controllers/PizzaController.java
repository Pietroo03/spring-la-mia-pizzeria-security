package org.exercise.spring.spring_la_mia_pizzeria_crud.controllers;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.OffertaSpeciale;
import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.exercise.spring.spring_la_mia_pizzeria_crud.service.IngredienteService;
import org.exercise.spring.spring_la_mia_pizzeria_crud.service.OffertaSpecialeService;
import org.exercise.spring.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private OffertaSpecialeService offertaSpecialeService;

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public String index(@RequestParam(name = "search", required = false, defaultValue = "") String search,
            Model model) {
        List<Pizza> pizzas;

        if (!search.isEmpty()) {
            pizzas = pizzaService.findByName(search);
        } else {
            pizzas = pizzaService.findAll();
        }

        model.addAttribute("pizzas", pizzas);
        model.addAttribute("search", search);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = pizzaService.getById(id);

        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienti", ingredienteService.findAll());

        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteService.findAll());
            return "pizzas/create";
        }

        Pizza newPizza = pizzaService.create(formPizza);

        return "redirect:/pizzas/" + newPizza.getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredienti", ingredienteService.findAll());
        model.addAttribute("pizza", pizzaService.getById(id));

        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteService.findAll());
            return "pizzas/edit";
        }

        Pizza newPizza = pizzaService.update(formPizza);

        return "redirect:/pizzas/" + newPizza.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Pizza pizza = pizzaService.getById(id);

        for (OffertaSpeciale offertaDaCancellare : pizza.getOfferteSpeciali()) {
            offertaSpecialeService.delete(offertaDaCancellare);
        }

        pizzaService.delete(pizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/new_offer")
    public String newOffer(@PathVariable Integer id, Model model) {
        OffertaSpeciale offertaSpeciale = new OffertaSpeciale();
        offertaSpeciale.setPizza(pizzaService.getById(id));

        model.addAttribute("offertaSpeciale", offertaSpeciale);
        return "offerte/create-or-edit";
    }

}
