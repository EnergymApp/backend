package com.energym.backend.reservas.controller;

import com.energym.backend.registrousuarios.model.Users;
import com.energym.backend.reservas.model.Bookings;
import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.model.Sessions;
import com.energym.backend.reservas.service.BookingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BookingsController {

    private final Logger log = LoggerFactory.getLogger(BookingsController.class);

    @Autowired
    public BookingsService service;

    @Operation(summary = "Agrega un nuevo registro en la tabla bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La reserva creada"),
    })
    @PostMapping(value = "/bookings")
    public ResponseEntity<Bookings> newBooking(@RequestBody Bookings booking){
        Bookings created = service.newBooking(booking);
        log.info("Reserva creada correctamente: Id - " + created.getSession_id().getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca los usuarios participantes de una determinada sesión buscando por el id de la sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los usuarios participantes de la sesión")
    })
    @GetMapping(value = "/bookings/session/")
    public ResponseEntity<List<Users>> findUsersBySession(@RequestParam Long session){
        return new ResponseEntity<>(service.findUsersBySession(session), HttpStatus.OK);
    }

    @Operation(summary = "Busca las sesiones a las que un usuario está apuntado buscando por el id del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones a las que el usuario está apuntado")
    })
    @GetMapping(value = "/bookings/user/")
    public ResponseEntity<List<Sessions>> findClassesByUser(@RequestParam Integer user){
        return new ResponseEntity<>(service.findSessionsByUser(user), HttpStatus.OK);
    }

    @Operation(summary = "Busca las sesiones a las que un usuario está apuntado buscando por el id del usuario y un rango de fechas. Las fechas tienen formato yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones a las que el usuario está apuntado")
    })
    @GetMapping(value = "/bookings/user/between/")
    public ResponseEntity<List<Sessions>> findClassesByUserBetween(@RequestParam Integer user, @RequestParam LocalDate initialDate, @RequestParam LocalDate finalDate){
        return new ResponseEntity<>(service.findSessionsByUserBetween(user, initialDate, finalDate), HttpStatus.OK);
    }

    @Operation(summary = "Busca las sesiones que se impartirán en un rango de fechas. Las fechas tienen formato yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones que se impartirán en el rango de fechas")
    })
    @GetMapping(value = "/bookings/between/")
    public ResponseEntity<List<Sessions>> findClassesBetween(@RequestParam LocalDate initialDate, @RequestParam LocalDate finalDate){
        return new ResponseEntity<>(service.findSessionsBetween(initialDate, finalDate), HttpStatus.OK);
    }
}
