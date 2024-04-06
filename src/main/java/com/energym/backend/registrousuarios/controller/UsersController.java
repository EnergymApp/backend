package com.energym.backend.registrousuarios.controller;

import com.energym.backend.registrousuarios.model.Users;
import com.energym.backend.registrousuarios.service.UsersService;
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
public class UsersController {

    private final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersService service;

    @Operation(summary = "Agrega un nuevo registro en la tabla users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El usuario creado"),
            @ApiResponse(responseCode = "409", description = "Null cuando el id ya existe"),
            @ApiResponse(responseCode = "406", description = "Null cuando el email ya existe"),
            @ApiResponse(responseCode = "400", description = "Null cuando se produce un error al crear el usuario")
    })
    @PostMapping(value = "/user")
    public ResponseEntity<Users> newUser(@RequestBody Users user){
        try {
            Users found = service.findUserbyEmail(user.getEmail());
            if (found != null) {
                log.info("El email ya existe: " + user.getEmail());
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
            Optional<Users> foundId = service.findUserById(user.getId());
            if (foundId.isPresent()) {
                log.info("El id ya existe: " + user.getId());
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            Users created = service.newUser(user);
            log.info("Usuario creado correctamente: Nombre - " + created.getName());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("No se pudo crear el usuario: Nombre - " + user.getName());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca en la tabla users el usuario que coincida con el email pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario que coincida con el criterio de búsqueda"),
            @ApiResponse(responseCode = "404", description = "Null cuando el usuario no se encuentra")
    })
    @GetMapping("/user/email/")
    public ResponseEntity<Users> findUserByEmail(@RequestParam String email){
        try{
            Users found = service.findUserbyEmail(email);
            log.info("Usuario encontrado: Nombre - " + found.getName());
            return ResponseEntity.ok(found);
        }catch (Exception ex){
            log.error("Usuario no encontrado");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Busca en la tabla users el usuario que coincida con el id pasado como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario que coincida con el criterio de búsqueda"),
            @ApiResponse(responseCode = "404", description = "Null cuando el usuario no se encuentra")
    })
    @GetMapping("/user/id/")
    public ResponseEntity<Users> findUserById(@RequestParam Integer id){
        Optional<Users> found = service.findUserById(id);
        if (found.isPresent()){
            log.info("Usuario encontrado: Nombre - " + found.get().getName());
            return ResponseEntity.ok(found.get());
        }
        else {
            log.error("Usuario no encontrado");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Lista de todos los usuarios almacenados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios"),
    })
    @GetMapping("/user/")
    public ResponseEntity<List<Users>> getUsers(){
        log.info("Lista de usuarios");
        return ResponseEntity.ok(service.getUsers());
    }

    @Operation(summary = "Busca el usuario que coincida con el email y el password que llegan en el body del request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario que coincida con los criterios de búsqueda"),
            @ApiResponse(responseCode = "404", description = "Null cuando el usuario no se encuentra"),
    })
    @PostMapping(value = "/login")
    public ResponseEntity<Users> login(@RequestBody Users user){
        try{
            Users found = service.login(user.getEmail(), user.getPassword());
            log.info("Usuario found: Nombre - " + found.getName());
            return new ResponseEntity<>(found, HttpStatus.OK);
        }catch(Exception ex){
            log.error("Usuario no encontrado");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualiza la información de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario actualizado"),
            @ApiResponse(responseCode = "409", description = "Null cuando no se actualiza el usuario"),
    })
    @PutMapping(value = "/user/")
    public ResponseEntity<Users> updateUser(@RequestBody Users user){
        try{
            Users updated = service.updateUser(user);
            log.info("Rest request actualizar un usuario: " + updated.getId() + " - " + updated.getName());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            log.info("Rest bad request actualizar un usuario " + user.getId());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Actualiza el password de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario actualizado"),
            @ApiResponse(responseCode = "409", description = "Null cuando no se actualiza el usuario"),
    })
    @PutMapping(value = "/user/update_password/")
    public ResponseEntity<Users> updatePassword(@RequestBody Users user){
        try{
            Users updated = service.updatePassword(user.getId(), user.getPassword());
            log.info("Rest request actualizar un usuario: " + updated.getId() + " - " + updated.getName());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            log.info("Rest bad request actualizar un usuario " + user.getId());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
