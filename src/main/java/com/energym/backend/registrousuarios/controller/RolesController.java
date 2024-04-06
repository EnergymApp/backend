package com.energym.backend.registrousuarios.controller;

import com.energym.backend.registrousuarios.model.Roles;
import com.energym.backend.registrousuarios.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RolesController {
    private final Logger log = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    public RolesService service;

    @Operation(summary = "Busca un rol por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El rol qu coincide con el parámetro de búsqueda"),
            @ApiResponse(responseCode = "409", description = "Null cuando no se encuentra el rol"),
    })
    @GetMapping("/role/id/")
    public ResponseEntity<Roles> findRoleById(@RequestParam Integer id){
        try {
            Optional<Roles> found = service.findRoleById(id);
            if (!found.isPresent()) {
                log.info("No se encontró el rol con id: " + id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            log.info("Rol encontrado: " + found.get().getName());
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Lista con todos los roles almacenados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista con todos los roles"),
    })
    @GetMapping("/role/")
    public ResponseEntity<List<Roles>> getRoles(){
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }
}
