package net.timxekhach.operation.rest.service.trip;

import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.repository.SeatTypeRepository;
import net.timxekhach.operation.data.repository.TripRepository;
import net.timxekhach.operation.data.repository.TripUserSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSeatService extends SeatAbstractService{

    private TripRepository tripRepository;
    private SeatTypeRepository seatTypeRepository;
    private TripUserSeatRepository tripUserSeatRepository;

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Autowired
    public void setSeatTypeRepository(SeatTypeRepository seatTypeRepository) {
        this.seatTypeRepository = seatTypeRepository;
    }
    @Autowired
    public void setTripUserSeatRepository(TripUserSeatRepository tripUserSeatRepository) {
        this.tripUserSeatRepository = tripUserSeatRepository;
    }

    @Override
    public List<SeatType> getSeatTypeList(Long tripId) {
        Trip trip = tripRepository.findTripByTripId(tripId);
        List<SeatType> seatTypes = seatTypeRepository.findAllByBussTypeId(trip.getBuss().getBussTypeId());
        return seatTypes;
    }

    @Override
    public List<SeatType> getBookedSeatTypeList(Long tripId) {
        Trip trip = tripRepository.findTripByTripId(tripId);
        //validate here
        List<SeatType> bookedSeats = tripUserSeatRepository.findAllByTripId(trip.getTripId());

        return bookedSeats;
    }
}
