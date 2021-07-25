package net.timxekhach.operation.rest.service.trip;

import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.entity.Trip;

import java.util.List;

public interface ITripService {
    List<Trip>      getTripList();
    Trip            getTrip(Long tripId);
}
