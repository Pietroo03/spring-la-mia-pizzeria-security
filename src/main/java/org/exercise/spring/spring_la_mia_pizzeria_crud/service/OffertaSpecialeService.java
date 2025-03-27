package org.exercise.spring.spring_la_mia_pizzeria_crud.service;

import java.util.List;

import org.exercise.spring.spring_la_mia_pizzeria_crud.model.OffertaSpeciale;
import org.exercise.spring.spring_la_mia_pizzeria_crud.repository.OffertaSpecialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffertaSpecialeService {

    @Autowired
    private OffertaSpecialeRepository offertaSpecialeRepository;

    public List<OffertaSpeciale> findAll() {
        return offertaSpecialeRepository.findAll();
    }

    public OffertaSpeciale getById(Integer id) {
        return offertaSpecialeRepository.findById(id).get();
    }

    public OffertaSpeciale create(OffertaSpeciale offerta) {
        return offertaSpecialeRepository.save(offerta);
    }

    public OffertaSpeciale update(OffertaSpeciale offerta) {
        return offertaSpecialeRepository.save(offerta);
    }

    public void delete(OffertaSpeciale offerta) {

        offertaSpecialeRepository.delete(offerta);
    }

    public void deleteById(Integer id) {

        OffertaSpeciale offerta = getById(id);

        offertaSpecialeRepository.delete(offerta);
    }
}
