package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import net.timxekhach.operation.rest.service.TripService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.http.ResponseEntity;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.entity.Location;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/trip"})
public class TripApi {

    private final TripService tripService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	@GetMapping("/searchLocation/{searchString}")
	public ResponseEntity<List<Location>> searchLocation(@PathVariable("searchString") String searchString) {
		return success(tripService.searchLocation(searchString));
	}
	@GetMapping("/findScheduledLocations/{mark0ForStart}/{startLocationId}/{endLocationId}/{searchString}")
	public ResponseEntity<List<Location>> findScheduledLocations(
			@PathVariable("mark0ForStart") Long mark0ForStart,
			@PathVariable("searchString") String searchString,
			@PathVariable("startLocationId") Long startLocationId,
			@PathVariable("endLocationId") Long endLocationId
	) {
		return success(tripService.findScheduledLocations(mark0ForStart, startLocationId, endLocationId, searchString));
	}
	@GetMapping("/findBussSchedules/{launchDate}/{locationFromId}/{locationToId}")
	public ResponseEntity<List<BussSchedule>> findBussSchedules (@PathVariable("launchDate") String launchDate, @PathVariable("locationFromId") Long locationFromId, @PathVariable("locationToId") Long locationToId) {
		return success(tripService.findBussSchedules(launchDate, locationFromId, locationToId));
	}
	@PostMapping("/findScheduledLocationsContains/{searchString}")
	public ResponseEntity<List> findScheduledLocationsContains (@PathVariable("searchString") String searchString) {
		return success(tripService.findScheduledLocationsContains(searchString));
	}

	@PostMapping("/findLocationsHasScheduleGoThroughLocation/{searchString}/{locationId}")
	public ResponseEntity<List> findLocationsHasScheduleGoThroughLocation (@PathVariable("searchString") String searchString, @PathVariable("locationId") Long locationId) {
		return success(tripService.findLocationsHasScheduleGoThroughLocation(searchString, locationId));
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
