package net.timxekhach.operation.rest.service.trip;

import net.timxekhach.operation.data.entity.SeatType;

import java.util.List;

public interface ISeatService {
    List<SeatType> getSeatTypeList(Long tripId);
    List<SeatType> getBookedSeatTypeList(Long tripId);
}
