package com.energym.backend.reservas.repository;

import com.energym.backend.registrousuarios.model.Users;
import com.energym.backend.reservas.model.Bookings;
import com.energym.backend.reservas.model.BookingsPK;
import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.model.Sessions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface BookingsRepository extends CrudRepository<Bookings, BookingsPK> {

    @Modifying
    @Transactional
    @Query(value = "insert into bookings(user_id, session_id) VALUES (:userId, :sessionId)", nativeQuery = true)
    void newBooking(@Param("userId") Integer userId, @Param("sessionId") Long sessionId);

    @Query("select b from Bookings b where b.session_id.id = :sessionId and b.user_id.id = :userId")
    Bookings findBooking(Integer userId, Long sessionId);

    @Query("select u from Bookings b join Users u on b.user_id.id = u.id where b.session_id.id = :sessionId")
    List<Users> findUsersByClass(Long sessionId);

    @Query("select c from Bookings b join Sessions c on b.session_id.id = c.id where b.user_id.id = :user")
    List<Sessions> findSessionsByUser(Integer user);

    @Query("select c from Bookings b join Sessions c on b.session_id.id = c.id where b.user_id.id = :user and c.date between :initialDate and :finalDate")
    List<Sessions> findSessionsByUserBetween(Integer user, LocalDate initialDate, LocalDate finalDate);

    @Query("select c from Bookings b join Sessions c on b.session_id.id = c.id where c.date between :initialDate and :finalDate")
    List<Sessions> findSessionsBetween(LocalDate initialDate, LocalDate finalDate);
}
