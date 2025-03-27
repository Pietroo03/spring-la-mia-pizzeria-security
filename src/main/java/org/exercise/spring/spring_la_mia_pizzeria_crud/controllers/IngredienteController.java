package org.exercise.spring.spring_la_mia_pizzeria_crud.controllers;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.exercise.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.exercise.spring.spring_la_mia_pizzeria_crud.service.IngredienteService;
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
    private IngredienteService ingredienteService;

    @GetMapping
    public String index(@RequestParam(name = "search", required = false, defaultValue = "") String search,
            Model model) {
        List<Ingrediente> ingredienti;

        if (!search.isEmpty()) {
            ingredienti = ingredienteService.findByName(search);
        } else {
            ingredienti = ingredienteService.findAll();
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

        ingredienteService.create(formIngrediente);

        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingrediente", ingredienteService.getById(id));
        model.addAttribute("edit", true);

        return "ingredienti/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingrediente") Ingrediente newIngrediente,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }

        ingredienteService.update(newIngrediente);

        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Ingrediente ingredienteDaEliminare = ingredienteService.getById(id);

        for (Pizza pizzaCollegata : ingredienteDaEliminare.getPizze()) {
            pizzaCollegata.getIngredienti().remove(ingredienteDaEliminare);
        }

        ingredienteService.delete(ingredienteDaEliminare);

        return "redirect:/ingredients";
    }
}
