package com.energym.backend.reservas.controller;

import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.model.Professionals;
import com.energym.backend.reservas.model.Sessions;
import com.energym.backend.reservas.service.SessionsService;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class SessionsController {

    private final Logger log = LoggerFactory.getLogger(SessionsController.class);

    @Autowired
    public SessionsService service;

    @Operation(summary = "Agrega un nuevo registro en la tabla sessions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La sesión creada"),
            @ApiResponse(responseCode = "409", description = "null cuando la sesión no se pudo crear")
    })
    @PostMapping(value = "/sessions")
    public ResponseEntity<Sessions> newSession(@RequestBody Sessions session){
        Optional<Sessions> created = service.newSession(session);
        if (created.isPresent()) {
            log.info("Sesión creada correctamente: Fecha - " + created.get().getDate());
            return new ResponseEntity<>(created.get(), HttpStatus.CREATED);
        }
        log.info("No se pudo crear la sesión: Fecha - " + created.get().getDate());
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @Operation(summary = "Busca en la tabla sessions la sesión que coincida con el id pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "La sesión que coincida con el criterio de búsqueda"),
            @ApiResponse(responseCode = "404", description = "Null cuando la sesión no se encuentra")
    })
    @GetMapping(value = "/sessions/id/")
    public ResponseEntity<Sessions> findSessionById(@RequestParam Long id){
        Optional<Sessions> found = service.findSessionById(id);
        if (found.isPresent()) {
            log.info("Clase encontrada: Fecha - " + found.get().getDate());
            return new ResponseEntity<>(found.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Busca en la tabla sessions todas las sesiones que coincida con la fecha pasada como parámetro. La fecha tiene formato yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones que coincidan con los criterios de búsqueda")
    })
    @GetMapping(value = "/sessions/date/")
    public ResponseEntity<List<Sessions>> findSessionsByDate(@RequestParam LocalDate date){
        return new ResponseEntity<>(service.findSessionsByDate(date), HttpStatus.OK);
    }

    @Operation(summary = "Busca en la tabla sessions todas las sesiones que se encuentren entre las dos fechas pasadas como parámetro. Las fechas tienen formato yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones que coincidan con los criterios de búsqueda")
    })
    @GetMapping(value = "/sessions/date_between/")
    public ResponseEntity<List<Sessions>> findSessionsByDateBetween(@RequestParam LocalDate initialDate, @RequestParam LocalDate finalDate){
        return new ResponseEntity<>(service.findSessionsByDateBetween(initialDate, finalDate), HttpStatus.OK);
    }

    @Operation(summary = "Busca en la tabla sessions todas las sesiones que coincida con la fecha y hora pasados como parámetro. La fecha tiene formato yyyy-MM-dd y la hora HH:mm:ss")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones que coincidan con los criterios de búsqueda")
    })
    @GetMapping(value = "/sessions/date_hour/")
    public ResponseEntity<List<Sessions>> findSessionsByDateAndHour(@RequestParam LocalDate date, @RequestParam LocalTime hour){
        return new ResponseEntity<>(service.findSessionsByDateAndHour(date, hour), HttpStatus.OK);
    }

    @Operation(summary = "Busca en la tabla sessions todas las sesiones cuyo profesional tenga el id pasado como parámetro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones que coincidan con los criterios de búsqueda")
    })
    @GetMapping(value = "/sessions/professional/")
    public ResponseEntity<List<Sessions>> findSessionsByOwner(@RequestParam Integer id){
        Professionals professional = new Professionals();
        professional.setId(id);
        return new ResponseEntity<>(service.findSessionsByOwner(professional), HttpStatus.OK);
    }

    @Operation(summary = "Busca en la tabla sessions todas las sesiones cuyo clase tenga el id pasado como parámetro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las sesiones que coincidan con los criterios de búsqueda")
    })
    @GetMapping(value = "/sessions/class/")
    public ResponseEntity<List<Sessions>> findSessionsByClass(@RequestParam String id){
        Classes classes = new Classes();
        classes.setCode(id);
        return new ResponseEntity<>(service.findSessionsByClass(classes), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza la información de una sesión.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La sesión actualizada"),
            @ApiResponse(responseCode = "409", description = "Null cuando no se actualiza la sesión")
    })
    @PutMapping(value = "/sessions")
    public ResponseEntity<Sessions> updateSession(@RequestBody Sessions session){
        try{
            Optional<Sessions> updated = service.updateSession(session);
            if(updated.isPresent()){
                log.info("Rest request actualizar una clase: Nombre - " + updated.get().getDate());
                return new ResponseEntity<>(updated.get(), HttpStatus.OK);
            }
            log.info("Rest bad request actualizar una clase: Fecha - " + session.getDate());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }catch(Exception ex){
            log.info("Rest bad request actualizar una clase: Nombre - " + session.getDate());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
