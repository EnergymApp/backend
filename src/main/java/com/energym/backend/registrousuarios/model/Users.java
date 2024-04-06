package com.energym.backend.registrousuarios.model;

import com.energym.backend.reservas.model.Sessions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class Users {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;


    @Column(name = "email", unique = true)
    @Getter
    @Setter
    private String email;


    @Column(name = "password")
    @Getter
    @Setter
    private String password;


    @Column(name = "telephone")
    @Getter
    @Setter
    private String telephone;


    @JoinColumn(name = "role")
    @ManyToOne
    @Getter
    @Setter
    private Roles role;

    @JoinTable(name = "bookings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    @ManyToMany
    private Set<Sessions> bookings;

    public Users(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Users(Integer id) {
        this.id = id;
    }
}
