package com.energym.backend.registrousuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "role")
    private Set<Users> users;

    public Roles(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Roles(Integer id) {
        this.id = id;
    }
}
