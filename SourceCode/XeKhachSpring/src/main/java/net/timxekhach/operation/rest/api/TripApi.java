package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.entity.TripUser;
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

import java.util.Arrays;
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
	@GetMapping("/getTripWithPreparedTripUser/{tripId}/{tripUserId}")
	public ResponseEntity<Trip> getTripWithPreparedTripUser(@PathVariable("tripId") Long tripId,
													@PathVariable("tripUserId") Long tripUserId) {
		return success(tripService.getTripWithPreparedTripUser(tripId, tripUserId));
	}

	@GetMapping("/getTripUsers")
	public ResponseEntity<List<TripUser>> getTripUsers(@RequestParam Long userId,
													   @RequestParam String phones,
													   @RequestParam String emails) {
		List<String> phoneList = Arrays.asList(phones.split(","));
		List<String> emailList = Arrays.asList(emails.split(","));
		return success(tripService.getTripUsers(userId, phoneList, emailList));
	}

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
