package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.SeatType;
import org.springframework.web.bind.annotation.*;
import net.timxekhach.operation.rest.service.TripService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
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
	public ResponseEntity<List> availableTrips () {
		return success(tripService.availableTrips());
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}