package org.exercise.spring.spring_la_mia_pizzeria_crud.controllers;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.exercise.spring.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
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
@RequestMapping("/ingredients")
public class IngredienteController {

    @Autowired
    private IngredienteRepository repository;

    @GetMapping
    public String index(@RequestParam(name = "search", required = false, defaultValue = "") String search,
            Model model) {
        List<Ingrediente> ingredienti;

        if (!search.isEmpty()) {
            ingredienti = repository.findByNomeContainingIgnoreCase(search);
        } else {
            ingredienti = repository.findAll();
        }

        model.addAttribute("ingredienti", ingredienti);
        model.addAttribute("search", search);
        return "ingredienti/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());

        return "ingredienti/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredienti/create-or-edit";
        }

        repository.save(formIngrediente);

        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingrediente", repository.findById(id).get());
        model.addAttribute("edit", true);

        return "ingredienti/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingrediente") Ingrediente newIngrediente,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }

        repository.save(newIngrediente);

        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Ingrediente ingredienteDaEliminare = repository.findById(id).get();

        for (Pizza pizzaCollegata : ingredienteDaEliminare.getPizze()) {
            pizzaCollegata.getIngredienti().remove(ingredienteDaEliminare);
        }

        repository.delete(ingredienteDaEliminare);

        return "redirect:/ingredients";
    }
}
