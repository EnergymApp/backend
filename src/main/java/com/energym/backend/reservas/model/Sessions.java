package com.energym.backend.reservas.model;

import com.energym.backend.registrousuarios.model.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
public class Sessions {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private LocalDate date;

    @Column(name = "hour")
    @Temporal(TemporalType.TIME)
    @Getter
    @Setter
    private LocalTime hour;

    @JoinColumn(name = "owner")
    @ManyToOne
    @Getter
    @Setter
    private Professionals owner;

    @JoinColumn(name = "classes")
    @ManyToOne
    @Getter
    @Setter
    private Classes classes;

    @ManyToMany(mappedBy = "bookings")
    private Set<Users> users;
}
