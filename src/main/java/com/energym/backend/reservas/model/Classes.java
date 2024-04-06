package com.energym.backend.reservas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "classes")
@NoArgsConstructor
public class Classes {

    @Id
    @Column(name = "code")
    @Getter
    @Setter
    private String code;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Getter
    @Setter
    private String description;

    @Column(name = "price")
    @Getter
    @Setter
    private Double price;

    @Column(name = "duration")
    @Getter
    @Setter
    private Integer duration;

    @Column(name = "image", columnDefinition = "TEXT")
    @Getter
    @Setter
    private String image;

    @Column(name = "rating")
    @Getter
    @Setter
    private Float rating;

    @JoinColumn(name = "classes")
    @OneToMany
    private Set<Sessions> sessions;

    public Classes(String code) {
        this.code = code;
    }

    public Classes(String id, String name, String description, Double price, Integer duration, String image, Float rating) {
        this.code = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.image = image;
        this.rating = rating;
    }
}
