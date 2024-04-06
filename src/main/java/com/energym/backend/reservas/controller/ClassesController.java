package com.energym.backend.reservas.controller;

import com.energym.backend.reservas.model.Classes;
import com.energym.backend.reservas.service.ClassesService;
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
public class ClassesController {

    private final Logger log = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    public ClassesService service;

    @PostMapping(value = "/classes/all")
    public ResponseEntity<List<Classes>> newClasses(@RequestBody List<Classes> classes){
        List<Classes> created = service.newClasses(classes);
        log.info("Clases creadas correctamente");
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Agrega un nuevo registro en la tabla classes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La clase creada"),
    })
    @PostMapping(value = "/classes")
    public ResponseEntity<Classes> newClass(@RequestBody Classes classes){
        Classes created = service.newClass(classes);
        log.info("Clase creada correctamente: Nombre - " + created.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca en la tabla classes la clase que coincida con el código pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "La clase que coincida con el criterio de búsqueda"),
            @ApiResponse(responseCode = "404", description = "Null cuando la clase no se encuentra")
    })
    @GetMapping(value = "/classes/code/")
    public ResponseEntity<Classes> findClassById(@RequestParam String code){
        Optional<Classes> found = service.findClassById(code);
        if (found.isPresent()) {
            log.info("Clase encontrada: Name - " + found.get().getName());
            return new ResponseEntity<>(found.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Busca en la tabla classes todas las clases que coincida con el nombre pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las clases que coincidan con el criterio de búsqueda")
    })
    @GetMapping(value = "/classes/name/")
    public ResponseEntity<List<Classes>>findClassByName(@RequestParam String name){
        return new ResponseEntity<>(service.findClassByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Busca todas las clases en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas las clases en la base de datos")
    })
    @GetMapping(value = "/classes")
    public ResponseEntity<List<Classes>> getClasses(){
        return new ResponseEntity<>(service.getClasses(), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza la información de una clase.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La clase actualizada"),
            @ApiResponse(responseCode = "409", description = "Null cuando no se actualiza la clase"),
    })
    @PutMapping(value = "/classes")
    public ResponseEntity<Classes> updateClass(@RequestBody Classes classes){
        try{
            Optional<Classes> updated = service.updateClass(classes);
            if(updated.isPresent()){
                log.info("Rest request actualizar una clase: Nombre - " + updated.get().getName());
                return new ResponseEntity<>(updated.get(), HttpStatus.OK);
            }
            log.info("Rest bad request actualizar una clase: Nombre - " + classes.getName());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }catch(Exception ex){
            log.info("Rest bad request actualizar una clase: Nombre - " + classes.getName());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
