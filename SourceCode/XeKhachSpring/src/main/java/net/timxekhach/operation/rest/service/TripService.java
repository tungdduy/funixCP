package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import net.timxekhach.operation.response.ErrorCode;
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

	public List availableSeats (Map<String, String> data) {
		// TODO : service availableSeats method
		return null;
	}

	public List availableTrips () {
		// TODO : service availableTrips method
		return null;
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
