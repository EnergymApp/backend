package com.energym.backend.reservas.service;

import com.energym.backend.reservas.model.Professionals;
import com.energym.backend.reservas.repository.ProfessionalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalsService {

    private final ProfessionalsRepository repository;

    public ProfessionalsService(ProfessionalsRepository repository) {
        this.repository = repository;
    }

    public Professionals newProfessional(Professionals professional){
        return repository.save(professional);
    }

    public Optional<Professionals> findProfessionalById(Integer id){
        return repository.findById(id);
    }

    public List<Professionals> findProfessionalByArea(String area){
        return repository.findProfessionalsByArea(area);
    }

    public List<Professionals> getProfessionals(){
        return (List<Professionals>) repository.findAll();
    }

    public Optional<Professionals> updateProfessional(Professionals professional){
        repository.updateProfessional(professional.getName(), professional.getTelephone(), professional.getArea(), professional.getId());
        return repository.findById(professional.getId());
    }
}
