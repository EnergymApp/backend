package com.energym.backend.reservas.service;

import com.energym.backend.registrousuarios.model.Users;
import com.energym.backend.reservas.model.Bookings;
import com.energym.backend.reservas.model.Sessions;
import com.energym.backend.reservas.repository.BookingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingsService {

    private final BookingsRepository repository;

    public BookingsService(BookingsRepository repository) {
        this.repository = repository;
    }

    public Bookings newBooking(Bookings booking){
        repository.newBooking(booking.getUser_id().getId(), booking.getSession_id().getId());
        return repository.findBooking(booking.getUser_id().getId(), booking.getSession_id().getId());
    }

    public List<Users> findUsersBySession(Long session){
        return repository.findUsersByClass(session);
    }

    public List<Sessions> findSessionsByUser(Integer user){
        return repository.findSessionsByUser(user);
    }

    public List<Sessions> findSessionsByUserBetween(Integer user, LocalDate initialDate, LocalDate finalDate){
        return repository.findSessionsByUserBetween(user, initialDate, finalDate);
    }

    public List<Sessions> findSessionsBetween(LocalDate initialDate, LocalDate finalDate){
        return repository.findSessionsBetween(initialDate, finalDate);
    }
}
