package com.energym.backend.registrousuarios.service;

import com.energym.backend.registrousuarios.model.Roles;
import com.energym.backend.registrousuarios.repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    private final RolesRepository repository;

    public RolesService(RolesRepository repository) {
        this.repository = repository;
    }

    public Optional<Roles> findRoleById(Integer id){
        return repository.findById(id);
    }

    public List<Roles> getRoles(){
        return (List<Roles>) repository.findAll();
    }
}
