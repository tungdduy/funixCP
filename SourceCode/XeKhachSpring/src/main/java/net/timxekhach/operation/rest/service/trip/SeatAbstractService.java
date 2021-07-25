package net.timxekhach.operation.rest.service.trip;

import net.timxekhach.operation.data.entity.SeatType;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SeatAbstractService implements ISeatService {
    protected Long tripId;
    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public List<SeatType> getAvailableSeats(){
        List<SeatType> allSeats = getSeatTypeList(tripId);
        List<SeatType> bookedSeats = getBookedSeatTypeList(tripId);

        List<SeatType> availableSeat = allSeats.stream().filter(s -> !bookedSeats.contains(s)).collect(Collectors.toList());

        return availableSeat;
    }
}
