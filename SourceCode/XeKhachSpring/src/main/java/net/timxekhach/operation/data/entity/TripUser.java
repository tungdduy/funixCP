package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class TripUser extends TripUser_MAPPED {

    public TripUser() {}
    public TripUser(Trip trip, User user) {
        super(trip, user);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

