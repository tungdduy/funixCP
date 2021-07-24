package net.timxekhach.operation.rest.service.ticket;

import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTripService extends TripAbstractService {

    private TripRepository tripRepository;
    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> getTripBussList() {
        List<Trip> tripList = tripRepository.findByStartPointAndEndPointAndStatus(startPoint, endPoint);
        if (startTime != null ){
            tripList.removeIf( t -> t.getStartTime().getTime() == startTime);
        }
        return tripList;
    }
}
