package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import javax.persistence.Entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class TripUserSeat extends TripUserSeat_MAPPED {

    public TripUserSeat() {}
    public TripUserSeat(SeatType seatType, Trip trip, User user) {
        super(seatType, trip, user);
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //


}

