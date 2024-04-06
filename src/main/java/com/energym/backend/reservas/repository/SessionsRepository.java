package com.energym.backend.reservas.repository;

import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.model.Professionals;
import com.energym.backend.reservas.model.Sessions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SessionsRepository extends CrudRepository<Sessions, Long> {

    Optional<Sessions> findById(Long id);

    List<Sessions> findSessionsByDate(LocalDate date);

    List<Sessions> findSessionsByDateAndHour(LocalDate date, LocalTime hour);

    List<Sessions> findSessionsByDateBetween(LocalDate initialDate, LocalDate finalDate);

    List<Sessions> findSessionsByOwner(Professionals owner);

    List<Sessions> findSessionsByClasses(Classes classes);

    @Modifying
    @Transactional
    @Query("update Sessions u set u.date=:date, u.hour=:hour, u.owner=:owner, u.classes=:classes where u.id=:id")
    void updateSession(@Param("id") Long id, @Param("date") LocalDate date, @Param("hour") LocalTime hour, @Param("owner") Professionals owner, @Param("classes") Classes classes);
}
