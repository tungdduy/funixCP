package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import java.util.List;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import java.util.stream.Collectors;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import javax.persistence.Entity;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Trip extends Trip_MAPPED {

    public Trip() {}
    public Trip(Buss buss) {
        super(buss);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //

public List<SeatType> availableSeats() {
        List<SeatType> allSeatTypes = this.buss.getBussType().getAllSeatTypes();
        List<SeatType> myTripUserSeats = this.getAllTripUserSeats().stream()
                .map(TripUserSeat_MAPPED::getSeatType)
                .collect(Collectors.toList());
        allSeatTypes.removeAll(myTripUserSeats);
        return allSeatTypes;
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

