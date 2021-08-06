package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import java.util.List;
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import net.timxekhach.operation.data.entity.Trip;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.repository.TripRepository;
import lombok.RequiredArgsConstructor;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

	private final TripRepository tripRepository;

	public List<SeatType> availableSeats (Map<String, String> data) {
		Trip trip = ErrorCode.TRIP_NOT_FOUND.throwIfNull(tripRepository.getById(Trip.pk(data)));
		return trip.availableSeats();
	}
	public List availableTrips () {
		// TODO : service availableTrips method
		return null;
	}

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
