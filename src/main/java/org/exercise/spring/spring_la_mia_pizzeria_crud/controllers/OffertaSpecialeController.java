package org.exercise.spring.spring_la_mia_pizzeria_crud.controllers;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.OffertaSpeciale;
import org.exercise.spring.spring_la_mia_pizzeria_crud.repository.OffertaSpecialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OffertaSpecialeController {

    @Autowired
    private OffertaSpecialeRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offertaSpeciale") OffertaSpeciale newOffer, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "offerte/create-or-edit";
        }

        repository.save(newOffer);

        return "redirect:/pizzas/" + newOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("offertaSpeciale", repository.findById(id).get());
        model.addAttribute("edit", true);

        return "offerte/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("offertaSpeciale") OffertaSpeciale newOffer,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "offerte/create-or-edit";
        }

        repository.save(newOffer);

        return "redirect:/pizzas/" + newOffer.getPizza().getId();
    }
}
