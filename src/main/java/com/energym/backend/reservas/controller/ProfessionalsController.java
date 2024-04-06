package com.energym.backend.reservas.controller;

import com.energym.backend.reservas.model.Professionals;
import com.energym.backend.reservas.service.ProfessionalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProfessionalsController {

    private final Logger log = LoggerFactory.getLogger(ProfessionalsController.class);

    @Autowired
    public ProfessionalsService service;

    @Operation(summary = "Agrega un nuevo registro en la tabla professionals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El profesional creado"),
            @ApiResponse(responseCode = "409", description = "El profesional ya existe en la base de datos")
    })
    @PostMapping(value = "/professionals")
    public ResponseEntity<Professionals> newProfessional(@RequestBody Professionals professional){
        Optional<Professionals> found = service.findProfessionalById(professional.getId());
        if (found.isPresent()) {
            log.info("Rest bad request crear un profesional: Nombre - " + professional.getName());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        Professionals created = service.newProfessional(professional);
        log.info("Profesional creado correctamente: Nombre - " + created.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca en la tabla professionals al profesional que coincida con el id pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "El profesional que coincida con el criterio de búsqueda"),
            @ApiResponse(responseCode = "404", description = "Null cuando el profesional no se encuentra")
    })
    @GetMapping(value = "/professionals/id/")
    public ResponseEntity<Professionals> findProfessionalById(@RequestParam Integer id){
        Optional<Professionals> found = service.findProfessionalById(id);
        if (found.isPresent()) {
            log.info("Profesional encontrado: Nombre - " + found.get().getName());
            return new ResponseEntity<>(found.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Busca en la tabla professionals todos los profesionales que coincidan con el área pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los profesionales que coincidan con el criterio de búsqueda")
    })
    @GetMapping(value = "/professionals/area/")
    public ResponseEntity<List<Professionals>>findProfessionalsByArea(@RequestParam String area){
        return new ResponseEntity<>(service.findProfessionalByArea(area), HttpStatus.OK);
    }

    @Operation(summary = "Busca todos los profesionales en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos los profesionales en la base de datos")
    })
    @GetMapping(value = "/professionals")
    public ResponseEntity<List<Professionals>> getProfessionals(){
        return new ResponseEntity<>(service.getProfessionals(), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza la información de un profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El profesional actualizado"),
            @ApiResponse(responseCode = "409", description = "Null cuando no se actualiza el profesional"),
    })
    @PutMapping(value = "/professionals")
    public ResponseEntity<Professionals> updateProfessional(@RequestBody Professionals professional){
        try{
            Optional<Professionals> updated = service.updateProfessional(professional);
            log.info("Rest request actualizar un profesional: Nombre - " + updated.get());
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        }catch(Exception ex){
            log.info("Rest bad request actualizar una clase " + professional.getId());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
