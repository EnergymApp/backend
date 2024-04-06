package com.energym.backend.reservas.service;

import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.model.Professionals;
import com.energym.backend.reservas.model.Sessions;
import com.energym.backend.reservas.repository.SessionsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionsService {

    private final SessionsRepository repository;

    public SessionsService(SessionsRepository repository) {
        this.repository = repository;
    }

    public Optional<Sessions> newSession(Sessions sessions){
        return Optional.of(repository.save(sessions));
    }

    public Optional<Sessions> findSessionById(Long id){
        return repository.findById(id);
    }

    public List<Sessions> findSessionsByDate(LocalDate date){
        return repository.findSessionsByDate(date);
    }

    public List<Sessions> findSessionsByDateBetween(LocalDate InitialDate, LocalDate finalDate){
        return repository.findSessionsByDateBetween(InitialDate, finalDate);
    }

    public List<Sessions> findSessionsByDateAndHour(LocalDate date, LocalTime hour){
        return repository.findSessionsByDateAndHour(date, hour);
    }

    public List<Sessions> findSessionsByOwner(Professionals professional){
        return repository.findSessionsByOwner(professional);
    }

    public List<Sessions> findSessionsByClass(Classes classes){
        return repository.findSessionsByClasses(classes);
    }

    public Optional<Sessions> updateSession(Sessions sessions){
        repository.updateSession(sessions.getId(), sessions.getDate(), sessions.getHour(), sessions.getOwner(), sessions.getClasses());
        return repository.findById(sessions.getId());
    }
}
