package com.energym.backend.reservas.model;

import com.energym.backend.registrousuarios.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
//@Embeddable
public class BookingsPK implements Serializable {

    @Getter
    @Setter
    private Users user_id;

    @Getter
    @Setter
    private Sessions session_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingsPK bookingPK = (BookingsPK) o;
        return user_id.equals(bookingPK.getUser_id()) &&
                session_id.equals(bookingPK.getSession_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, session_id);
    }
}
