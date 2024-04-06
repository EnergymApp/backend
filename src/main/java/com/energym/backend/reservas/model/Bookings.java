package com.energym.backend.reservas.model;

import com.energym.backend.registrousuarios.model.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@IdClass(BookingsPK.class)
@NoArgsConstructor
public class Bookings {

    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne
    @Getter
    @Setter
    private Users user_id;

    @Id
    @JoinColumn(name = "session_id")
    @ManyToOne
    @Getter
    @Setter
    private Sessions session_id;
}
