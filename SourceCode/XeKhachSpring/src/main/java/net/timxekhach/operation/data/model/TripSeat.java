package net.timxekhach.operation.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TripSeat {
    Integer seatId;
    Boolean isLocked, isBooked, isFree;
}
