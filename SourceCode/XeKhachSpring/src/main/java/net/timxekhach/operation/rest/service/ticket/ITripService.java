package net.timxekhach.operation.rest.service.ticket;

import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.entity.TripBuss;

import java.util.List;

public interface ITripService {
    List<Trip> getTripBussList();
}
