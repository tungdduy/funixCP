package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.Location;
import net.timxekhach.operation.data.repository.LocationRepository;
import net.timxekhach.operation.data.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	private final TripRepository tripRepository;
	private final LocationRepository locationRepository;

	public List availableSeats (Map<String, String> data) {
		// TODO : service availableSeats method
		return null;
	}

	public List availableTrips () {
		// TODO : service availableTrips method
		return null;
	}
	public List<Location> searchLocation (String searchString) {
		return locationRepository.findTop20ByLocationNameContainsOrderByLocationIdDesc(searchString);
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
