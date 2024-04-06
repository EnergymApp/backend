package com.energym.backend.reservas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "professionals")
@NoArgsConstructor
public class Professionals {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "telephone")
    @Getter
    @Setter
    private String telephone;

    @Column(name = "area")
    @Getter
    @Setter
    private String area;

    @JoinColumn(name = "owner")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Sessions> sessions;

    public Professionals(Integer id) {
        this.id = id;
    }
}
