package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import java.util.List;
import java.util.Map;
import net.timxekhach.operation.rest.service.TripService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import net.timxekhach.operation.data.entity.SeatType;
import lombok.RequiredArgsConstructor;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/trip"})
public class TripApi {

    private final TripService tripService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //

	@PostMapping("/available-seats")
	public ResponseEntity<List<SeatType>> availableSeats (@RequestBody Map<String, String> data) {
		return success(tripService.availableSeats(data));
	}
	@PostMapping("/available-trips")
	/**
	 * Map contains:
	 * departureDate : departure date in string
	 * departurePoint : buss point id in string
	 * destinationPoint : buss point id in string
	 */
	public ResponseEntity<List> availableTrips (@RequestBody Map<String, String> data) {
		return success(tripService.availableTrips(data));
	}

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
