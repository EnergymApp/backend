package com.energym.backend.reservas.service;

import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.repository.ClassesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClassesService {

    private final ClassesRepository repository;

    public ClassesService(ClassesRepository repository) {
        this.repository = repository;
    }

    public List<Classes> newClasses(List<Classes> classes){
        return (List<Classes>) repository.saveAll(classes);
    }

    public Classes newClass(Classes classes){
        return repository.save(classes);
    }

    public Optional<Classes> findClassById(String id){
        return repository.findById(id);
    }

    public List<Classes> findClassByName(String name){
        return repository.findClassesByNameContainsIgnoreCase(name);
    }


    public List<Classes> getClasses(){
        return (List<Classes>) repository.findAll();
    }

    public Optional<Classes> updateClass(Classes classes){
        repository.updateClass(classes.getCode(), classes.getName(), classes.getDescription(), classes.getDuration(), classes.getImage(), classes.getPrice(), classes.getRating());
        return repository.findById(classes.getCode());
    }
}
