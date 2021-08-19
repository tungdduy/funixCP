package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.data.entity.Location;
import net.timxekhach.operation.data.repository.LocationRepository;
import java.util.Map;
import net.timxekhach.operation.data.repository.TripRepository;
import java.util.List;
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
